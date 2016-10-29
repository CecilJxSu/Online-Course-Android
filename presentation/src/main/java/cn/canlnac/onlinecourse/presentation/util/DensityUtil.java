package cn.canlnac.onlinecourse.presentation.util;

import android.content.Context;

/**
 * 密度转换工具.
 */

public class DensityUtil {
    private DensityUtil() {
    }

    /**
     * 设备独立像素单位转像素单位
     *
     * @param context 应用上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
