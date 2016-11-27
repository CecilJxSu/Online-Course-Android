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
 * 课程目录.
 */

public class CourseCatalogFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.course_catalogs, container, false);

        //绑定视图
        ButterKnife.bind(this, view);

        return view;
    }
}
