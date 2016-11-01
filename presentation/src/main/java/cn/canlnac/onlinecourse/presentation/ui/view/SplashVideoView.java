package cn.canlnac.onlinecourse.presentation.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * 启动页视频视图.
 */

public class SplashVideoView extends VideoView {
    public SplashVideoView(Context context) {
        super(context);
    }

    public SplashVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SplashVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重新设置视频位置，防止变形
     * @param widthMeasureSpec  宽测量值
     * @param heightMeasureSpec 高测量值
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(w,(int)(w/0.56f)); //设置视频视图的大小为当前视图的大小
    }
}
