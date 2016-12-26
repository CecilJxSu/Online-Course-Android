package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment1_1;
import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment2;
import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment3;

/**
 * Created by cecil on 2016/10/31.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabFragment1_1 tab1 = new TabFragment1_1();
                return tab1;
            case 1:
                TabFragment2 tab2 = new TabFragment2();
                return tab2;
            case 2:
                TabFragment3 tab3 = new TabFragment3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
