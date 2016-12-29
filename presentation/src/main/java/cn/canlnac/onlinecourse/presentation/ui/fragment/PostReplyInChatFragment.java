package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetReplyInChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerReplyCommentInChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetReplyModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ReplyCommentInChatModule;
import cn.canlnac.onlinecourse.presentation.model.ReplyModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetReplyInChatPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.ReplyCommentInChatPresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.CommentActivity;

/**
 * 话题发布评论.
 */

public class PostReplyInChatFragment extends BaseFragment {

    @BindView(R.id.comment_input) EditText comment;
    @BindView(R.id.comment_send) ImageView send;

    private int commentId = -1;
    private int toUserId = -1;
    private String toUserName;
    private boolean canSubmit = false;

    @Inject
    ReplyCommentInChatPresenter replyCommentInChatPresenter;
    @Inject
    GetReplyInChatPresenter getReplyInChatPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.comment_editor, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnTextChanged(R.id.comment_input)
    public void onTextChange(CharSequence s, int start, int before, int count) {
        if (comment.getText().length() > 0) {
            send.setColorFilter(0xFF00b2ff);
            canSubmit = true;
        } else {
            send.setColorFilter(0xFF6b6666);
            canSubmit = false;
        }
    }

    /**
     * 设置回复评论的数据
     * @param commentId
     * @param toUserId
     * @param toUserName
     */
    public void setReplyData(int commentId, int toUserId, String toUserName) {
        if (commentId > 0) {
            this.commentId = commentId;
            this.toUserId = toUserId;
            this.toUserName = toUserName;
            clearComment();
            comment.setHint("回复：@"+toUserName);
        }
    }

    @OnClick(R.id.comment_send)
    public void onClickSend(View v) {
        if (canSubmit) {
            //回复评论
            CommentActivity commentActivity = (CommentActivity)getActivity();
            if (commentActivity != null) {
                //评论内容
                Map<String,Object> reply = new HashMap<>();
                reply.put("toUserId", toUserId);
                reply.put("content", comment.getText().toString());

                DaggerReplyCommentInChatComponent.builder()
                        .applicationComponent(getApplicationComponent())
                        .activityModule(getActivityModule())
                        .replyCommentInChatModule(new ReplyCommentInChatModule(commentActivity.getChatId(), commentId, reply))
                        .build().inject(this);

                replyCommentInChatPresenter.setView(this);
                replyCommentInChatPresenter.initialize();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        View view = this.getView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void clearComment() {
        comment.setText("");
    }

    /**
     * 创建回复成功
     * @param replyId
     */
    public void postSuccess(Integer replyId) {
        CommentActivity commentActivity = (CommentActivity)getActivity();
        if (commentActivity != null) {
            commentActivity.toggleReplyFragment(0,0,"");
            clearComment();

            DaggerGetReplyInChatComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .getReplyModule(new GetReplyModule(replyId))
                    .build().inject(this);

            getReplyInChatPresenter.setView(this);
            getReplyInChatPresenter.initialize();
        }
    }

    /**
     * 获取新的回复
     * @param replyModel
     */
    public void getReply(ReplyModel replyModel) {
        CommentActivity commentActivity = (CommentActivity)getActivity();
        if (commentActivity != null) {
            commentActivity.postReply(commentId, replyModel);
        }
    }
}
