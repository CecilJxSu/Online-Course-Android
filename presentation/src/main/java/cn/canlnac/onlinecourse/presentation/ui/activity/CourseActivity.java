package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerFavoriteCourseComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetCatalogsComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetCourseComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerLikeCourseComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerUnfavoriteCourseComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerUnlikeCourseComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.FavoriteCourseModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCatalogsModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCourseModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.LikeCourseModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UnfavoriteCourseModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UnlikeCourseModule;
import cn.canlnac.onlinecourse.presentation.model.CatalogModel;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;
import cn.canlnac.onlinecourse.presentation.model.ReplyModel;
import cn.canlnac.onlinecourse.presentation.presenter.FavoriteCoursePresenter;
import cn.canlnac.onlinecourse.presentation.presenter.GetCatalogsPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.GetCoursePresenter;
import cn.canlnac.onlinecourse.presentation.presenter.LikeCoursePresenter;
import cn.canlnac.onlinecourse.presentation.presenter.UnfavoriteCoursePresenter;
import cn.canlnac.onlinecourse.presentation.presenter.UnlikeCoursePresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CoursePagerAdapter;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseCatalogFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseCommentFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseIntroFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInCourseFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostReplyInCourseFragment;
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

    @BindView(R.id.course_comment) ImageView courseComment;

    private PostCommentInCourseFragment commentFragment;
    private PostReplyInCourseFragment replyFragment;
    private boolean isShowFragment = true;
    private boolean isShowReplyFragment = true;

    private Boolean isLike = false;
    private Boolean isFavorite = false;
    private Boolean isShare = false;

    private int courseId;

    @Inject
    GetCoursePresenter getCoursePresenter;
    @Inject
    GetCatalogsPresenter getCatalogsPresenter;

    @Inject LikeCoursePresenter likeCoursePresenter;
    @Inject
    UnlikeCoursePresenter unlikeCoursePresenter;

    @Inject
    FavoriteCoursePresenter favoriteCoursePresenter;
    @Inject
    UnfavoriteCoursePresenter unfavoriteCoursePresenter;

    @BindView(R.id.course_video) SuperVideoPlayer mSuperVideoPlayer;
    @BindView(R.id.course_play_btn) View mPlayBtnView;
    private Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.course_details);

        ButterKnife.bind(this);

        //获取fragment
        commentFragment = ((PostCommentInCourseFragment)getSupportFragmentManager().findFragmentById(R.id.course_comment_fragment));
        replyFragment = ((PostReplyInCourseFragment) getSupportFragmentManager().findFragmentById(R.id.course_comment_reply_fragment));
        //隐藏fragment
        toggleCommentFragment();
        toggleReplyFragment();

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
        courseTab.addTab(courseTab.newTab().setText("文档"));

        //创建适配器
        final CoursePagerAdapter adapter = new CoursePagerAdapter
                (getSupportFragmentManager(), courseTab.getTabCount());
        //设置适配器
        coursePager.setAdapter(adapter);
        //缓存3个页面，如果不设置，第一页会重建
        coursePager.setOffscreenPageLimit(4);
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

        toggleLike(courseModel.isLike());

        toggleFavorite(courseModel.isFavorite());

        ((CourseIntroFragment)((CoursePagerAdapter)coursePager.getAdapter()).getItem(0)).showCourseInfo(courseModel);
    }

    /**
     * 获取课程ID
     * @return
     */
    public int getCourseId() {
        return this.courseId;
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
        if (courseId <= 0) {
            return;
        }
        if (isLike) {
            //取消点赞
            DaggerUnlikeCourseComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .unlikeCourseModule(new UnlikeCourseModule(courseId))
                    .build().inject(this);

            CourseActivity.this.unlikeCoursePresenter.setView(this);
            CourseActivity.this.unlikeCoursePresenter.initialize();
        } else {
            //点赞
            DaggerLikeCourseComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .likeCourseModule(new LikeCourseModule(courseId))
                    .build().inject(this);

            CourseActivity.this.likeCoursePresenter.setView(this);
            CourseActivity.this.likeCoursePresenter.initialize();
        }
    }

    /**
     * 点赞切换
     * @param isLike
     */
    public void toggleLike(boolean isLike) {
        this.isLike = isLike;
        if (isLike) {
            like.setImageResource(R.drawable.like_with_background);
        } else {
            like.setImageResource(R.drawable.unlike_with_background);
        }
    }

    @OnClick(R.id.course_comment)
    public void onClickComment(View v) {
        toggleCommentFragment();
        if (!isShowFragment) {
            commentFragment.clearComment();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isShowFragment) {
            toggleCommentFragment();
        }
        if (isShowReplyFragment) {
            toggleReplyFragment();
        }
        return super.onTouchEvent(event);
    }

    public void toggleCommentFragment() {
        isShowFragment = !isShowFragment;
        if (isShowFragment && !isShowReplyFragment) {
            getSupportFragmentManager().beginTransaction().show(commentFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(commentFragment).commit();
        }
    }

    private void toggleReplyFragment() {
        toggleReplyFragment(-1, -1, "");
    }

    public void toggleReplyFragment(int commentId, int toUserId, String toUserName) {
        isShowReplyFragment = !isShowReplyFragment;
        if (isShowReplyFragment && !isShowFragment) {
            replyFragment.setReplyData(commentId, toUserId, toUserName);
            getSupportFragmentManager().beginTransaction().show(replyFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(replyFragment).commit();
        }
    }

    /**
     * 发表评论
     * @param commentModel
     */
    public void postComment(CommentModel commentModel) {
        TabLayout.Tab commentTab = courseTab.getTabAt(2);
        if (commentTab != null) {
            commentTab.select();
        }

        ((CourseCommentFragment)((CoursePagerAdapter)coursePager.getAdapter()).getItem(2)).postComment(commentModel);
    }

    /**
     * 发表回复
     * @param replyModel
     */
    public void postReply(int commentId, ReplyModel replyModel) {
        TabLayout.Tab commentTab = courseTab.getTabAt(2);
        if (commentTab != null) {
            commentTab.select();
        }

        ((CourseCommentFragment)((CoursePagerAdapter)coursePager.getAdapter()).getItem(2)).postReply(commentId, replyModel);
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
        if (courseId <= 0) {
            return;
        }
        if (isFavorite) {
            //取消收藏
            DaggerUnfavoriteCourseComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .unfavoriteCourseModule(new UnfavoriteCourseModule(courseId))
                    .build().inject(this);

            CourseActivity.this.unfavoriteCoursePresenter.setView(this);
            CourseActivity.this.unfavoriteCoursePresenter.initialize();
        } else {
            //收藏
            DaggerFavoriteCourseComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .favoriteCourseModule(new FavoriteCourseModule(courseId))
                    .build().inject(this);

            CourseActivity.this.favoriteCoursePresenter.setView(this);
            CourseActivity.this.favoriteCoursePresenter.initialize();
        }
    }

    /**
     * 收藏切换
     * @param isFavorite
     */
    public void toggleFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        if (isFavorite) {
            favorite.setImageResource(R.drawable.favorite_green);
        } else {
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
