package cn.canlnac.onlinecourse.presentation.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不滑动翻页的ViewPager.
 */

public class NoPageChangeViewPager extends ViewPager {

    private boolean enablePaging = false;

    public NoPageChangeViewPager(Context context) {
        super(context);
    }

    public NoPageChangeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return enablePaging && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return enablePaging && super.onInterceptTouchEvent(ev);
    }

    public void setEnablePaging(boolean enablePaging) {
        this.enablePaging = enablePaging;
    }
}
