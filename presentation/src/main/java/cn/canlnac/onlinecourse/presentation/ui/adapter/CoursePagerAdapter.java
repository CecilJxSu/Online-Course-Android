package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseCatalogFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseCommentFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseIntroFragment;

/**
 * 课程详情中的页面适配器.
 */

public class CoursePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public CoursePagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CourseIntroFragment courseIntro = new CourseIntroFragment();
                return courseIntro;
            case 1:
                CourseCatalogFragment courseCatalog = new CourseCatalogFragment();
                return courseCatalog;
            case 2:
                CourseCommentFragment courseComment = new CourseCommentFragment();
                return courseComment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
