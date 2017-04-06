package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment1_1;
import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment2;
import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment3;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private List<Fragment> fragments = new ArrayList<>();

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;

        fragments.add(new TabFragment1_1());
        fragments.add(new TabFragment2());
        fragments.add(new TabFragment3());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
