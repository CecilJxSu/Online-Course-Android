package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.tedcoder.wkvideoplayer.model.Video;
import com.android.tedcoder.wkvideoplayer.model.VideoUrl;
import com.android.tedcoder.wkvideoplayer.util.DensityUtil;
import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;

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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.course_details);

        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);

        ButterKnife.bind(this);

        //获取意图和数据
        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);  //课程ID

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
            like.setImageResource(R.drawable.thump_up_green_icon);
        } else {
            like.setImageResource(R.drawable.thump_up_icon);
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
        finish();
    }

    public void changeVideo(String name, String url) {
        mSuperVideoPlayer.pausePlay(true);

        VideoUrl videoUrl = new VideoUrl();
        videoUrl.setFormatUrl(url);
        videoUrl.setFormatName("720P");
        videoUrl.setIsOnlineVideo(true);

        ArrayList<VideoUrl> arrayList = new ArrayList<>();
        arrayList.add(videoUrl);

        video = new Video();
        video.setVideoUrl(arrayList);
        video.setVideoName(name);
    }

    /**
     * 点赞按钮事件
     * @param v
     */
    @OnClick(R.id.course_like)
    public void onClickLike(View v) {
        if (isLike) {
            isLike = false;
            like.setImageResource(R.drawable.thump_up_icon);
        } else {
            isLike = true;
            like.setImageResource(R.drawable.thump_up_green_icon);
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
        if (video != null && video.getVideoUrl() != null && video.getVideoUrl().size() > 0) {
            mPlayBtnView.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.setAutoHideController(false);

            ArrayList<Video> videos = new ArrayList<>(1);
            videos.add(video);
            mSuperVideoPlayer.loadMultipleVideo(videos,0,0,0);
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
        /***
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
            float height = DensityUtil.dip2px(this, 200.f);
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
