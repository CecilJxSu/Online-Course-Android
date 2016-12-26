package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseListFragment;

/**
 * 课程页适配器.
 */

public class CourseListPagerAdapter extends FragmentStatePagerAdapter {
    String[] courseTypes;

    public CourseListPagerAdapter(FragmentManager fm, String[] courseTypes) {
        super(fm);
        this.courseTypes = courseTypes;
    }

    @Override
    public Fragment getItem(int position) {
        CourseListFragment courseListFragment = new CourseListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("courseType", courseTypes[position]);
        courseListFragment.setArguments(bundle);
        return courseListFragment;
    }

    @Override
    public int getCount() {
        return courseTypes.length;
    }
}
