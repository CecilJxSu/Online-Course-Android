package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.content.Context;

import q.rorbin.verticaltablayout.widget.QTabView;

/**
 * 首页，课程tab.
 */

public class TabAdapter implements q.rorbin.verticaltablayout.TabAdapter {
    private Context context;

    String[] tabTitles = {
            "计算机系",
            "网络系",
            "电子系",
            "软件系",
            "游戏系",
            "数码系",
            "管理系",
            "外语系",
            "国贸系",
            "财会系",
    };

    public TabAdapter(Context context) {
        this.context = context;
    }

    public String[] getTabTitles() {
        return tabTitles;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public int getBadge(int position) {
        return 0;
    }

    @Override
    public QTabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public QTabView.TabTitle getTitle(int position) {
        QTabView.TabTitle tabTitle = new QTabView.TabTitle.Builder(context)
                .setContent(tabTitles[position])
                .build();

        tabTitle.mColorSelected = 0xFF000000;
        tabTitle.mColorNormal = 0xFF666666;
        tabTitle.mTitleTextSize = 12;
        return tabTitle;
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }
}
