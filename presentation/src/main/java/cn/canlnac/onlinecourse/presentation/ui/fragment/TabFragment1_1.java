package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CourseListPagerAdapter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.TabAdapter;
import cn.canlnac.onlinecourse.presentation.ui.view.NoPageChangeViewPager;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * 第一页，主要关于课程.
 */

public class TabFragment1_1 extends Fragment {
    //tabs面板
    @BindView(R.id.tab_layout_1)
    VerticalTabLayout mTabLayout;

    //换页视图
    @BindView(R.id.pager_1)
    NoPageChangeViewPager mViewPager;

    private int currIndex = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.tab_fragment_1_1, container, false);

        //绑定视图
        ButterKnife.bind(this, view);

        TabAdapter tabAdapter = new TabAdapter(this.getActivity());
        //设置滑动页
        CourseListPagerAdapter coursePagerAdapter = new CourseListPagerAdapter(getFragmentManager(),tabAdapter.getTabTitles());
        mViewPager.setAdapter(coursePagerAdapter);
        //设置选项面板
        mTabLayout.setTabAdapter(tabAdapter);
        mTabLayout.getTabAt(0).setBackgroundColor(0xFFFFFFFF);
        mTabLayout.setOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                mTabLayout.getTabAt(currIndex).setBackgroundColor(0x666666);
                currIndex = position;
                tab.setBackgroundColor(0xFFFFFFFF);

                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        return view;
    }
}
