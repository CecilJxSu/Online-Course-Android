package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseCatalogFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseCommentFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseIntroFragment;

/**
 * 课程详情中的页面适配器.
 */

public class CoursePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private List<Fragment> fragmentList;
    public CoursePagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;

        fragmentList = new ArrayList<>();
        fragmentList.add(new CourseIntroFragment());
        fragmentList.add(new CourseCatalogFragment());
        fragmentList.add(new CourseCommentFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
