package cn.canlnac.onlinecourse.presentation.ui.view;

import android.content.Context;

/**
 * 加载数据视图接口.
 */

public interface LoadDataView {
    /**
     * 显示加载进度
     */
    void showLoading();

    /**
     * 隐藏加载进度
     */
    void hideLoading();


    /**
     * 显示重试
     */
    void showRetry();

    /**
     * 隐藏重试
     */
    void hideRetry();

    /**
     * 显示错误
     * @param message   错误内容
     */
    void showError(String message);

    /**
     * 获取上下文
     * @return {@link android.content.Context}
     */
    Context context();
}
