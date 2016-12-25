package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CourseGallerryAdapter;

/**
 * 第一页，主要关于课程.
 */

public class CourseListFragment extends Fragment {

    private String courseType;

    //课程列表
    @BindView(R.id.course_gallery)
    GridView gridView;

    //课程列表，图片列表显示
    int[] listImages = {
            R.drawable.list_image_1,
            R.drawable.list_image_2,
            R.drawable.list_image_1,
            R.drawable.list_image_2
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        if (savedInstanceState != null && savedInstanceState.getString("courseType") != null) {
            this.courseType = savedInstanceState.getString("courseType");
        }

        //绑定视图
        ButterKnife.bind(this, view);

        //设置适配器
        gridView.setAdapter(new CourseGallerryAdapter(this.getContext(), listImages));

        //设置item点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //打开课程详情页
                Intent intent = new Intent(CourseListFragment.this.getActivity(), CourseActivity.class);
                intent.putExtra("courseId", 1);      //课程ID
                CourseListFragment.this.startActivity(intent);
            }
        });

        //设置不滚动
        gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        return view;
    }
}
