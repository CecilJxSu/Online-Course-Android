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
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;
import cn.canlnac.onlinecourse.presentation.presenter.CreateCommentInCoursePresenter;
import cn.canlnac.onlinecourse.presentation.presenter.GetCommentInCoursePresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;

/**
 * 课程发布评论.
 */

public class PostReplyInCourseFragment extends BaseFragment {

    @BindView(R.id.comment_input) EditText comment;
    @BindView(R.id.comment_send) ImageView send;

    private int commentId = -1;
    private int toUserId = -1;
    private String toUserName;
    private boolean canSubmit = false;

    @Inject
    CreateCommentInCoursePresenter createCommentInCoursePresenter;
    @Inject
    GetCommentInCoursePresenter getCommentInCoursePresenter;

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
        this.commentId = commentId;
        this.toUserId = toUserId;
        this.toUserName = toUserName;
    }

    @OnClick(R.id.comment_send)
    public void onClickSend(View v) {
        Toast.makeText(this.getActivity(),"---------",Toast.LENGTH_SHORT).show();
        if (canSubmit) {
            //评论课程
            CourseActivity courseActivity = (CourseActivity)getActivity();
            if (courseActivity != null) {
                //评论内容

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
        CourseActivity courseActivity = (CourseActivity)getActivity();
        if (courseActivity != null) {
            courseActivity.onClickComment(null);
        }
    }

    /**
     * 获取新的评论
     * @param commentModel
     */
    public void getComment(CommentModel commentModel) {
        CourseActivity courseActivity = (CourseActivity)getActivity();
        if (courseActivity != null) {
            courseActivity.postComment(commentModel);
        }
    }
}
