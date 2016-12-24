package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.AndroidApplication;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetCommentsInCourseComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCommentsInCourseModule;
import cn.canlnac.onlinecourse.presentation.model.CommentListModel;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;
import cn.canlnac.onlinecourse.presentation.model.ReplyModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetCommentsInCoursePresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CommentAdapter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;

/**
 * 课程评论.
 */

public class CourseCommentFragment extends BaseFragment {
    @BindView(R.id.course_comments_list)
    ZrcListView zrcListView;

    private CommentAdapter adapter;
    private Handler handler;

    @Inject
    GetCommentsInCoursePresenter getCommentsInCoursePresenter;

    int start = 0;
    int count = 1;
    int total = 1;
    String sort = "date";

    List<CommentModel> comments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.course_comments, container, false);

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

        adapter = new CommentAdapter(getActivity(), comments);
        zrcListView.setAdapter(adapter);
        zrcListView.refresh(); // 主动下拉刷新

        return view;
    }

    @Override
    public void onDestroy() {
        zrcListView.setOnLoadMoreStartListener(null);
        zrcListView.setOnRefreshStartListener(null);

        super.onDestroy();
    }

    /**
     * 发表评论
     * @param commentModel
     */
    public void postComment(CommentModel commentModel) {
        comments.add(0,commentModel);
        adapter.notifyDataSetChanged();
    }

    /**
     * 发表回复
     * @param replyModel
     */
    public void postReply(int commentId, ReplyModel replyModel) {
        for (CommentModel commentModel:comments) {
            if (commentModel.getId() == commentId) {
                commentModel.getReplies().add(0,replyModel);
                adapter.notifyDataSetChanged();
                break;
            }
        }
    }

    /**
     * 刷新
     */
    private void refresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CourseActivity activity = (CourseActivity)getActivity();

                if (activity != null) {
                    if (activity.getCourseId() <= 0) {
                        zrcListView.setRefreshFail("课程不存在");
                        return;
                    }

                    if (start == 0) {
                        //获取评论
                            DaggerGetCommentsInCourseComponent.builder()
                                    .applicationComponent(getApplicationComponent())
                                    .activityModule(getActivityModule())
                                    .getCommentsInCourseModule(new GetCommentsInCourseModule(activity.getCourseId(), start, count, sort))
                                    .build().inject(CourseCommentFragment.this);

                            getCommentsInCoursePresenter.setView(CourseCommentFragment.this, 0);
                            getCommentsInCoursePresenter.initialize();

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
                    //获取评论
                    CourseActivity activity = (CourseActivity)getActivity();
                    if (activity != null) {
                        DaggerGetCommentsInCourseComponent.builder()
                                .applicationComponent(((AndroidApplication)getActivity().getApplication()).getApplicationComponent())
                                .activityModule(getActivityModule())
                                .getCommentsInCourseModule(new GetCommentsInCourseModule(activity.getCourseId(), start, count, sort))
                                .build().inject(CourseCommentFragment.this);

                        getCommentsInCoursePresenter.setView(CourseCommentFragment.this, 1);
                        getCommentsInCoursePresenter.initialize();
                    }
                }else{
                    zrcListView.stopLoadMore();
                }
            }
        }, 2 * 1000);
    }

    /**
     * 刷新显示评论
     * @param commentListModel
     */
    public void showRefreshComments(CommentListModel commentListModel) {
        total = commentListModel.getTotal();
        comments.addAll(commentListModel.getComments());
        adapter.notifyDataSetChanged();
        zrcListView.setRefreshSuccess("加载成功");  // 通知加载成功
        zrcListView.startLoadMore();                // 开启LoadingMore功能
    }

    /**
     * 更新显示评论
     * @param commentListModel
     */
    public void showLoadMoreComments(CommentListModel commentListModel) {
        total = commentListModel.getTotal();
        comments.addAll(commentListModel.getComments());
        adapter.notifyDataSetChanged();
        zrcListView.setLoadMoreSuccess();
    }

    /**
     * 刷新失败
     */
    public void showRefreshError(String message) {
        zrcListView.setRefreshFail(message);
    }
}
