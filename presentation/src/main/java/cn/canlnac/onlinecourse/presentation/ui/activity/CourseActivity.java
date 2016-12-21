package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetCatalogsComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetCourseComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCatalogsModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCourseModule;
import cn.canlnac.onlinecourse.presentation.model.CatalogModel;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetCatalogsPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.GetCoursePresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CoursePagerAdapter;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseCatalogFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseIntroFragment;
import cn.canlnac.onlinecourse.presentation.ui.widget.VideoPlayer.model.Video;
import cn.canlnac.onlinecourse.presentation.ui.widget.VideoPlayer.view.MediaController;
import cn.canlnac.onlinecourse.presentation.ui.widget.VideoPlayer.view.SuperVideoPlayer;
import cn.canlnac.onlinecourse.presentation.util.DensityUtil;

/**
 * 课程详情.
 */

public class CourseActivity extends BaseFragmentActivity implements View.OnClickListener {
    @BindView(R.id.course_close) ImageView close;
    @BindView(R.id.course_like) ImageView like;
    @BindView(R.id.course_share) ImageView share;
    @BindView(R.id.course_favorite) ImageView favorite;

    @BindView(R.id.course_watching_count) TextView watchingCount;
    @BindView(R.id.course_title) TextView courseTitle;

    @BindView(R.id.course_tab_layout) TabLayout courseTab;

    @BindView(R.id.course_pager) ViewPager coursePager;

    private Boolean isLike = false;
    private Boolean isFavorite = false;
    private Boolean isShare = false;

    private int courseId;

    @Inject
    GetCoursePresenter getCoursePresenter;

    @Inject
    GetCatalogsPresenter getCatalogsPresenter;

    @BindView(R.id.course_video) SuperVideoPlayer mSuperVideoPlayer;
    @BindView(R.id.course_play_btn) View mPlayBtnView;
    private Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.course_details);

        ButterKnife.bind(this);

        //获取意图和数据
        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);  //课程ID

        if (courseId == -1) {
            showToastMessage("没有指定课程");
            this.finish();
        }

        mPlayBtnView.setOnClickListener(this);
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);

        courseTab.addTab(courseTab.newTab().setText("简介"));
        courseTab.addTab(courseTab.newTab().setText("目录"));
        courseTab.addTab(courseTab.newTab().setText("评论"));

        //创建适配器
        final CoursePagerAdapter adapter = new CoursePagerAdapter
                (getSupportFragmentManager(), courseTab.getTabCount());
        //设置适配器
        coursePager.setAdapter(adapter);
        //添加换页事件
        coursePager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(courseTab));
        //设置选项卡选择事件
        courseTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                coursePager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        coursePager.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                //获取课程
                DaggerGetCourseComponent.builder()
                        .applicationComponent(getApplicationComponent())
                        .activityModule(getActivityModule())
                        .getCourseModule(new GetCourseModule(CourseActivity.this.courseId))
                        .build().inject(CourseActivity.this);

                CourseActivity.this.getCoursePresenter.setView(CourseActivity.this);
                CourseActivity.this.getCoursePresenter.initialize();

                //获取目录
                DaggerGetCatalogsComponent.builder()
                        .applicationComponent(getApplicationComponent())
                        .activityModule(getActivityModule())
                        .getCatalogsModule(new GetCatalogsModule(CourseActivity.this.courseId))
                        .build().inject(CourseActivity.this);
                CourseActivity.this.getCatalogsPresenter.setView(CourseActivity.this);
                CourseActivity.this.getCatalogsPresenter.initialize();
            }

            @Override
            public void onViewDetachedFromWindow(View view) {

            }
        });
    }

    /**
     * 显示课程信息
     * @param courseModel
     */
    public void showCourse(CourseModel courseModel) {
        //观看人数
        String watchingCountStr = courseModel.getWatchCount() + "";
        watchingCount.setText(watchingCountStr);
        //课程标题
        courseTitle.setText(courseModel.getName());

        if (courseModel.isLike()) {
            like.setImageResource(R.drawable.like_with_background);
        } else {
            like.setImageResource(R.drawable.unlike_with_background);
        }

        if (courseModel.isFavorite()) {
            favorite.setImageResource(R.drawable.favorite_green);
        } else {
            favorite.setImageResource(R.drawable.unfavorite);
        }


        ((CourseIntroFragment)((CoursePagerAdapter)coursePager.getAdapter()).getItem(0)).showCourseInfo(courseModel);
    }

    /**
     * 显示目录
     * @param catalogModelList
     */
    public void showCatalogs(List<CatalogModel> catalogModelList) {
        if (null == catalogModelList) {
            catalogModelList = new ArrayList<>();
        }
        ((CourseCatalogFragment)((CoursePagerAdapter)coursePager.getAdapter()).getItem(1)).showCatalogs(catalogModelList);
    }

    /**
     * 返回按钮事件
     * @param v
     */
    @OnClick(R.id.course_close)
    public void onClickClose(View v) {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode  == KeyEvent.KEYCODE_BACK && getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void changeVideo(String url) {
        mVideoPlayCallback.onCloseVideo();

        video = new Video();
        video.setPlayUrl(url);
    }

    /**
     * 点赞按钮事件
     * @param v
     */
    @OnClick(R.id.course_like)
    public void onClickLike(View v) {
        if (isLike) {
            isLike = false;
            like.setImageResource(R.drawable.unlike_with_background);
        } else {
            isLike = true;
            like.setImageResource(R.drawable.like_with_background);
        }
    }

    /**
     * 分享按钮事件
     * @param v
     */
    @OnClick(R.id.course_share)
    public void onClickShare(View v) {
        if (isShare) {
            isShare = false;
            share.setImageResource(R.drawable.share_green_icon);
        } else {
            isShare = true;
            share.setImageResource(R.drawable.share_icon);
        }
    }

    /**
     * 收藏按钮事件
     * @param v
     */
    @OnClick(R.id.course_favorite)
    public void onClickFavorite(View v) {
        if (isFavorite) {
            isFavorite = false;
            favorite.setImageResource(R.drawable.favorite_green);
        } else {
            isFavorite = true;
            favorite.setImageResource(R.drawable.unfavorite);
        }
    }

    @Override
    public void onClick(View view) {
        if (video != null && video.getPlayUrl() != null) {
            mPlayBtnView.setVisibility(View.GONE);
            mSuperVideoPlayer.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.setAutoHideController(true);

            mSuperVideoPlayer.loadMultipleVideo(video,0);
        } else {
            showToastMessage("没有视频链接");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /***
     * 旋转屏幕之后回调
     *
     * @param newConfig newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (null == mSuperVideoPlayer) return;

        /**
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mSuperVideoPlayer.getLayoutParams().height = (int) width;
            mSuperVideoPlayer.getLayoutParams().width = (int) height;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 220.f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
        }
    }

    /***
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
        }
    }

    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        @Override
        public void onCloseVideo() {
            mSuperVideoPlayer.close();
            mPlayBtnView.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.setVisibility(View.GONE);
            resetPageToPortrait();
        }

        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
            }
        }

        @Override
        public void onPlayFinish() {

        }
    };
}
