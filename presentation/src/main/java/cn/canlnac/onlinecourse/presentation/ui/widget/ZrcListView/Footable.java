package cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView;

import android.graphics.Canvas;

public interface Footable {
    boolean draw(Canvas canvas, int left, int top, int right, int bottom);

    int getHeight();
}
