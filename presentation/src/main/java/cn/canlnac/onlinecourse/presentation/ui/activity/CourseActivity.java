package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CoursePagerAdapter;

/**
 * 课程详情.
 */

public class CourseActivity extends FragmentActivity {
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
        int courseId = intent.getIntExtra("courseId", -1);  //课程ID

        //观看人数
        watchingCount.setText("1.1 万");
        //课程标题
        courseTitle.setText("课程标题");

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
    }

    /**
     * 返回按钮事件
     * @param v
     */
    @OnClick(R.id.course_close)
    public void onClickClose(View v) {
        finish();
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
}
