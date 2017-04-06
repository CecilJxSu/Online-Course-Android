package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetCommentsInChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCommentsInChatModule;
import cn.canlnac.onlinecourse.presentation.model.CommentListModel;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;
import cn.canlnac.onlinecourse.presentation.model.ReplyModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetCommentsInChatPresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CommentInChatAdapter;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInChatFragment;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostReplyInChatFragment;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;

/**
 * 话题评论.
 */

public class CommentActivity extends BaseFragmentActivity {
    @BindView(R.id.chat_comments_list) ZrcListView zrcListView;

    private CommentInChatAdapter adapter;
    private Handler handler;

    @Inject
    GetCommentsInChatPresenter getCommentsInChatPresenter;

    private PostCommentInChatFragment commentFragment;
    private PostReplyInChatFragment replyFragment;
    private boolean isShowFragment = true;
    private boolean isShowReplyFragment = true;

    int start = 0;
    int count = 1;
    int total = 1;
    String sort = "date";

    private int chatId;

    List<CommentModel> comments = new ArrayList<>();

    private int newCommentCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_comments);
        ButterKnife.bind(this);

        //获取fragment
        commentFragment = ((PostCommentInChatFragment)getSupportFragmentManager().findFragmentById(R.id.chat_comment_fragment));
        replyFragment = ((PostReplyInChatFragment) getSupportFragmentManager().findFragmentById(R.id.chat_comment_reply_fragment));
        //隐藏fragment
        toggleCommentFragment();
        toggleReplyFragment();

        chatId = getIntent().getIntExtra("chatId", -1);

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

        zrcListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isShowFragment) {
                    toggleCommentFragment();
                }
                if (isShowReplyFragment) {
                    toggleReplyFragment();
                }
                return false;
            }
        });

        adapter = new CommentInChatAdapter(this, comments);
        zrcListView.setAdapter(adapter);
        zrcListView.refresh(); // 主动下拉刷新
    }

    public int getChatId (){
        return this.chatId;
    }

    @Override
    public void onDestroy() {
        zrcListView.setOnLoadMoreStartListener(null);
        zrcListView.setOnRefreshStartListener(null);

        super.onDestroy();
    }

    @OnClick(R.id.chat_comment_close)
    public void onClickClose(View v) {
        Intent intent = new Intent();
        intent.putExtra("newCommentCount", newCommentCount);
        setResult(200, intent);

        this.finish();
    }

    @OnClick(R.id.chat_comment_btn)
    public void onClickComment(View v) {
        toggleCommentFragment();
        if (!isShowFragment) {
            commentFragment.clearComment();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isShowFragment) {
            toggleCommentFragment();
        }
        if (isShowReplyFragment) {
            toggleReplyFragment();
        }
        return super.onTouchEvent(event);
    }

    public void toggleCommentFragment() {
        isShowFragment = !isShowFragment;
        if (isShowFragment && !isShowReplyFragment) {
            getSupportFragmentManager().beginTransaction().show(commentFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(commentFragment).commit();
        }
    }

    private void toggleReplyFragment() {
        toggleReplyFragment(-1, -1, "");
    }

    public void toggleReplyFragment(int commentId, int toUserId, String toUserName) {
        isShowReplyFragment = !isShowReplyFragment;
        if (isShowReplyFragment && !isShowFragment) {
            replyFragment.setReplyData(commentId, toUserId, toUserName);
            getSupportFragmentManager().beginTransaction().show(replyFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(replyFragment).commit();
        }
    }

    /**
     * 发表评论
     * @param commentModel
     */
    public void postComment(CommentModel commentModel) {
        comments.add(0,commentModel);
        adapter.notifyDataSetChanged();

        newCommentCount += 1;
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
                if (start == 0) {
                    //获取评论
                    DaggerGetCommentsInChatComponent.builder()
                            .applicationComponent(getApplicationComponent())
                            .activityModule(getActivityModule())
                            .getCommentsInChatModule(new GetCommentsInChatModule(chatId, start, count, sort))
                            .build().inject(CommentActivity.this);

                    getCommentsInChatPresenter.setView(CommentActivity.this, 0);
                    getCommentsInChatPresenter.initialize();

                } else {
                    showRefreshError("加载完成");
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
                    //获取评论
                    DaggerGetCommentsInChatComponent.builder()
                            .applicationComponent(getApplicationComponent())
                            .activityModule(getActivityModule())
                            .getCommentsInChatModule(new GetCommentsInChatModule(chatId, start, count, sort))
                            .build().inject(CommentActivity.this);

                    getCommentsInChatPresenter.setView(CommentActivity.this, 1);
                    getCommentsInChatPresenter.initialize();

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

    /**
     * 刷新失败
     */
    public void showRefreshError(String message) {
        zrcListView.setRefreshFail(message);
    }
}
