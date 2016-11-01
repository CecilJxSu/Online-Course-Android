package cn.canlnac.onlinecourse.presentation.ui.layout;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.util.DensityUtil;

/**
 * 启动页的指示器.
 */

public class SplashIndicator extends LinearLayout {
    //指示器数量
    private final int INDICATOR_COUNT = 3;
    //指示器视图
    private List<ImageView> mImageList = new ArrayList<>();

    public SplashIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        for (int i = 0; i < INDICATOR_COUNT; i++) {
            ImageView imageView = new ImageView(getContext());
            if (i == 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_selected));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_unselected));
            }

            //布局参数
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //设置边距
            params.setMargins(DensityUtil.dp2px(getContext(), 10), 0, DensityUtil.dp2px(getContext(), 10), 0);
            //添加视图
            addView(imageView, params);
            mImageList.add(imageView);
        }
    }

    /**
     * 页面切换时，改变指示器
     * @param position  页面位置
     */
    public void setSelected(int position) {
        for (int i = 0; i < mImageList.size(); i++) {
            ImageView imageView = mImageList.get(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_selected));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.circle_unselected));
            }
        }
    }
}
