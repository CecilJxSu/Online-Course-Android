package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 课程评论.
 */

public class CourseCommentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.course_comments, container, false);

        //绑定视图
        ButterKnife.bind(this, view);

        return view;
    }
}
