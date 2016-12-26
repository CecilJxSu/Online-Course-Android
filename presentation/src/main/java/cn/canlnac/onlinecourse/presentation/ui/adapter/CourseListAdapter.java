package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;

/**
 * 课程适配器.
 */

public class CourseListAdapter extends BaseAdapter {
    private Activity activity;
    private List<CourseModel> courses;

    public CourseListAdapter(Activity activity, List<CourseModel> courses) {
        this.activity = activity;
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return courses==null ? 0 : courses.size();
    }
    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CourseListViewHolder holder;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.course_review, null);
        }

        holder = new CourseListViewHolder(view, courses.get(position));
        view.setTag(holder);

        return view;
    }
}
