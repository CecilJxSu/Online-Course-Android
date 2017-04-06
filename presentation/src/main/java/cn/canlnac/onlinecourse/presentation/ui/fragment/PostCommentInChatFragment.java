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
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerCreateCommentInChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetCommentInChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.CreateCommentInChatModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCommentModule;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;
import cn.canlnac.onlinecourse.presentation.presenter.CreateCommentInChatPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.GetCommentInChatPresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.CommentActivity;

/**
 * 话题发布评论.
 */

public class PostCommentInChatFragment extends BaseFragment {

    @BindView(R.id.comment_input) EditText comment;
    @BindView(R.id.comment_send) ImageView send;

    private boolean canSubmit = false;

    @Inject
    CreateCommentInChatPresenter createCommentInChatPresenter;
    @Inject
    GetCommentInChatPresenter getCommentInChatPresenter;

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

    @OnClick(R.id.comment_send)
    public void onClickSend(View v) {
        if (canSubmit) {
            //评论话题
            CommentActivity commentActivity = (CommentActivity)getActivity();
            if (commentActivity != null) {
                //评论内容
                Map<String,Object> commentData = new HashMap<>();
                commentData.put("content", comment.getText().toString());

                DaggerCreateCommentInChatComponent.builder()
                        .applicationComponent(getApplicationComponent())
                        .activityModule(getActivityModule())
                        .createCommentInChatModule(new CreateCommentInChatModule(commentActivity.getChatId(), commentData))
                        .build().inject(this);

                createCommentInChatPresenter.setView(this);
                createCommentInChatPresenter.initialize();
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
     * 创建评论成功
     * @param commentId
     */
    public void postSuccess(Integer commentId) {
        CommentActivity commentActivity = (CommentActivity)getActivity();
        if (commentActivity != null) {
            commentActivity.onClickComment(null);

            DaggerGetCommentInChatComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .getCommentModule(new GetCommentModule(commentId))
                    .build().inject(this);

            getCommentInChatPresenter.setView(this);
            getCommentInChatPresenter.initialize();
        }
    }

    /**
     * 获取新的评论
     * @param commentModel
     */
    public void getComment(CommentModel commentModel) {
        CommentActivity commentActivity = (CommentActivity)getActivity();
        if (commentActivity != null) {
            commentActivity.postComment(commentModel);
        }
    }
}
