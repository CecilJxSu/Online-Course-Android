package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetMyRepliesComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMyRepliesModule;
import cn.canlnac.onlinecourse.presentation.model.CommentListModel;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetMyRepliesPresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.MyRepliesCommentAdapter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;

/**
 * 我的回复.
 */

public class MyRepliesActivity extends BaseActivity {

    @BindView(R.id.mychats_list) ZrcListView zrcListView;

    @BindView(R.id.my_chats_title) TextView title;

    private MyRepliesCommentAdapter adapter;
    private Handler handler;

    int start = 0;
    int count = 10;
    int total = 10;

    List<CommentModel> comments = new ArrayList<>();

    @Inject
    GetMyRepliesPresenter getMyRepliesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychats);
        //绑定视图
        ButterKnife.bind(this);

        title.setText("我的回复");

        handler = new Handler();

        // 设置默认偏移量，主要用于实现透明标题栏功能。（可选）
        float density = getResources().getDisplayMetrics().density;
        zrcListView.setFirstTopOffset((int) (0 * density));

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        zrcListView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
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

        adapter = new MyRepliesCommentAdapter(this, comments);
        zrcListView.setAdapter(adapter);
        zrcListView.refresh(); // 主动下拉刷新
    }

    @OnClick(R.id.close)
    public void onClickClose(View v) {
        finish();
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
                start = 0;
                comments.clear();
                adapter.notifyDataSetChanged();

                if (start == 0) {
                    //获取我的话题
                    initialize(0);
                }
            }
        }, 200);
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
                    //获取我的话题
                    initialize(1);
                }else{
                    zrcListView.stopLoadMore();
                }
            }
        }, 1000);
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

    public void initialize(int state){
        DaggerGetMyRepliesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .getMyRepliesModule(new GetMyRepliesModule(start,count))
                .build().inject(this);

        getMyRepliesPresenter.setView(this, state);
        getMyRepliesPresenter.initialize();
    }
}
