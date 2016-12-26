package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetCoursesComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCoursesModule;
import cn.canlnac.onlinecourse.presentation.model.CourseListModel;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetCoursesPresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CourseListAdapter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;

/**
 * 第一页，主要关于课程.
 */

public class CourseListFragment extends BaseFragment {

    @BindView(R.id.course_list)
    ZrcListView zrcListView;

    private CourseListAdapter adapter;
    private Handler handler;

    int start = 0;
    int count = 10;
    int total = 10;
    String sort = "date";

    private String[] courseTypes = new String[1];

    List<CourseModel> courses = new ArrayList<>();

    @Inject
    GetCoursesPresenter getCoursesPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.course_list, container, false);

        if (getArguments() != null && getArguments().getString("courseType") != null) {
            this.courseTypes[0] = getArguments().getString("courseType");
        }

        //绑定视图
        ButterKnife.bind(this, view);

        handler = new Handler();

        // 设置默认偏移量，主要用于实现透明标题栏功能。（可选）
        float density = getResources().getDisplayMetrics().density;
        zrcListView.setFirstTopOffset((int) (0 * density));

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this.getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        zrcListView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this.getActivity());
        footer.setCircleColor(0xff33bbee);
        zrcListView.setFootable(footer);

        // 设置列表项出现动画（可选）
        zrcListView.setItemAnimForTopIn(R.anim.topitem_in);
        zrcListView.setItemAnimForBottomIn(R.anim.bottomitem_in);

        // 下拉刷新事件回调（可选）
        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        // 加载更多事件回调（可选）
        zrcListView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });

        zrcListView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(CourseListFragment.this.getActivity(), CourseActivity.class);
                intent.putExtra("courseId", courses.get(position).getId());      //课程ID
                CourseListFragment.this.startActivity(intent);
            }
        });

        adapter = new CourseListAdapter(getActivity(), courses);
        zrcListView.setAdapter(adapter);
        zrcListView.refresh(); // 主动下拉刷新

        return view;
    }

    /**
     * 刷新失败
     */
    public void showRefreshError(String message) {
        zrcListView.setRefreshFail(message);
    }

    @Override
    public void onDestroy() {
        zrcListView.setOnLoadMoreStartListener(null);
        zrcListView.setOnRefreshStartListener(null);

        super.onDestroy();
    }

    /**
     * 刷新
     */
    private void refresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (start == 0) {
                    //获取课程列表
                    if (getActivity() != null) {
                        DaggerGetCoursesComponent.builder()
                                .applicationComponent(getApplicationComponent())
                                .activityModule(getActivityModule())
                                .getCoursesModule(new GetCoursesModule(start, count, sort, Arrays.asList(courseTypes)))
                                .build().inject(CourseListFragment.this);

                        getCoursesPresenter.setView(CourseListFragment.this, 0);
                        getCoursesPresenter.initialize();
                    } else {
                        showRefreshError("加载完成");
                    }
                }
            }
        }, 2 * 1000);
    }

    /**
     * 加载更多
     */
    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start += count;
                if(start < total){
                    if (getActivity() != null) {
                        //获取课程列表
                        DaggerGetCoursesComponent.builder()
                                .applicationComponent(getApplicationComponent())
                                .activityModule(getActivityModule())
                                .getCoursesModule(new GetCoursesModule(start, count, sort, Arrays.asList(courseTypes)))
                                .build().inject(CourseListFragment.this);

                        getCoursesPresenter.setView(CourseListFragment.this, 1);
                        getCoursesPresenter.initialize();
                    }
                }else{
                    zrcListView.stopLoadMore();
                }
            }
        }, 2 * 1000);
    }

    /**
     * 刷新显示课程
     * @param courseListModel
     */
    public void showRefreshCourses(CourseListModel courseListModel) {
        total = courseListModel.getTotal();
        courses.addAll(courseListModel.getCourses());
        adapter.notifyDataSetChanged();
        zrcListView.setRefreshSuccess("加载成功");  // 通知加载成功
        zrcListView.startLoadMore();                // 开启LoadingMore功能
    }

    /**
     * 更新显示课程
     * @param courseListModel
     */
    public void showLoadMoreCourses(CourseListModel courseListModel) {
        total = courseListModel.getTotal();
        courses.addAll(courseListModel.getCourses());
        adapter.notifyDataSetChanged();
        zrcListView.setLoadMoreSuccess();
    }
}
