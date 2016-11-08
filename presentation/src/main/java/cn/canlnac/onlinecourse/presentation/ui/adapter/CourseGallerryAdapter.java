package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 课程列表浏览.
 */

public class CourseGallerryAdapter extends BaseAdapter {
    private Context context;
    private int[] images;

    @BindView(R.id.course_gallery_text)
    TextView textView;

    @BindView(R.id.course_gallery_image)
    ImageView imageView;

    public CourseGallerryAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;

        if (null == convertView) {
            gridView = LayoutInflater.from(context).inflate(R.layout.course_gallery, null);

            ButterKnife.bind(this, gridView);

            textView.setText("course test");

            imageView.setImageResource(images[position]);
        } else {
            gridView = convertView;
        }

        return gridView;
    }
}
