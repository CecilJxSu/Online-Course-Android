package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;

/**
 * 课程 view holder.
 */

public class CourseListViewHolder {

    @BindView(R.id.course_preview_img)
    SimpleDraweeView previewImg;
    @BindView(R.id.course_list_name)
    TextView courseName;
    @BindView(R.id.course_list_intro)
    TextView courseIntro;
    @BindView(R.id.course_list_author)
    TextView courseAuthor;
    @BindView(R.id.course_list_watch)
    TextView courseWatch;

    public CourseListViewHolder(View view, CourseModel course) {
        ButterKnife.bind(this, view);

        previewImg.setImageURI(course.getPreviewUrl());

        courseName.setText(course.getName());

        String intro = (course.getIntroduction() == null)?"暂无简介":course.getIntroduction().substring(0, 25) + "...";
        courseIntro.setText(intro);

        courseAuthor.setText(course.getAuthor().getName());
        courseWatch.setText("观看: " + course.getWatchCount());
    }
}
