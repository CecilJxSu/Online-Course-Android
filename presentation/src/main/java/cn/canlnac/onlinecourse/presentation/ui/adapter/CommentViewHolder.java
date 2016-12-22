package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.AndroidApplication;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerLikeCommentComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerUnlikeCommentComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.LikeCommentModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UnlikeCommentModule;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;
import cn.canlnac.onlinecourse.presentation.presenter.LikeCommentPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.UnlikeCommentPresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;

/**
 * 评论 view holder.
 */

public class CommentViewHolder {
    @BindView(R.id.comment_head_icon) SimpleDraweeView userIcon;
    @BindView(R.id.comment_user_name) TextView userName;
    @BindView(R.id.comment_content) TextView content;
    @BindView(R.id.comment_time) TextView postTime;
    @BindView(R.id.comment_like_count) TextView likeCount;
    @BindView(R.id.comment_reply) ImageView reply;
    @BindView(R.id.comment_like) ImageView like;

    @Nullable
    @BindView(R.id.comment_reply_list)
    ListView replyView;

    private Activity activity;
    private PrettyTime prettyTime;

    @Inject LikeCommentPresenter likeCommentPresenter;
    @Inject UnlikeCommentPresenter unlikeCommentPresenter;

    private boolean isLike;
    private boolean isReply;
    private int commentId;

    public CommentViewHolder(Activity activity, View view, CommentModel comment, PrettyTime prettyTime) {
        ButterKnife.bind(this, view);

        this.activity = activity;
        this.prettyTime = prettyTime;

        userIcon.setImageURI(comment.getAuthor().getIconUrl());
        userName.setText(comment.getAuthor().getName());
        content.setText(comment.getContent());
        postTime.setText(prettyTime.format(new Date(comment.getDate())));
        likeCount.setText(comment.getLikeCount()+"");

        this.commentId = comment.getId();

        changeLike(comment.isLike());

        changeReply(comment.isReply());

        //回复评论
        if (comment.getReplies() != null && comment.getReplies().size() > 0) {
            ReplyAdapter adapter = new ReplyAdapter(activity, comment.getReplies(), prettyTime);
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View item = adapter.getView(i, null, replyView);
                item.measure(0,0);
                totalHeight += item.getMeasuredHeight();
            }
            replyView.setAdapter(adapter);
            ViewGroup.LayoutParams params = replyView.getLayoutParams();
            params.height = totalHeight + replyView.getDividerHeight() * (adapter.getCount()-1)+30;
            replyView.setLayoutParams(params);
            replyView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    /**
     * 点赞
     * @param v
     */
    @OnClick(R.id.comment_like)
    public void onClickLike(View v) {
        if (isLike) {
            //取消点赞
            DaggerUnlikeCommentComponent.builder()
                    .applicationComponent(((AndroidApplication) activity.getApplication()).getApplicationComponent())
                    .activityModule(new ActivityModule(activity))
                    .unlikeCommentModule(new UnlikeCommentModule(commentId))
                    .build()
                    .inject(this);

            unlikeCommentPresenter.setView(this);
            unlikeCommentPresenter.initialize();
        } else {
            //点赞
            DaggerLikeCommentComponent.builder()
                    .applicationComponent(((AndroidApplication) activity.getApplication()).getApplicationComponent())
                    .activityModule(new ActivityModule(activity))
                    .likeCommentModule(new LikeCommentModule(commentId))
                    .build()
                    .inject(this);

            likeCommentPresenter.setView(this);
            likeCommentPresenter.initialize();
        }
    }

    /**
     * 改变点赞状态
     * @param isLike
     */
    public void changeLike(boolean isLike) {
        this.isLike = isLike;
        if (isLike) {
            like.setImageResource(R.drawable.thump_up_green_icon);
        } else {
            like.setImageResource(R.drawable.thump_up_icon);
        }
    }

    /**
     * 修改点赞数
     * @param isLike
     */
    public void changeLikeCount(boolean isLike) {
        int count = Integer.parseInt(likeCount.getText().toString());
        if (isLike) {
            likeCount.setText(++count+"");
        } else {
            likeCount.setText(--count+"");
        }
    }

    /**
     * 改变回复评论状态
     * @param isReply
     */
    public void changeReply(boolean isReply) {
        this.isReply = isReply;
        if (isReply) {
            reply.setImageResource(R.drawable.comment_green_icon);
        } else {
            reply.setImageResource(R.drawable.comment_icon);
        }
    }

    /**
     * 显示消息
     * @param message   消息
     */
    public void showToastMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 未登陆用户需要登陆
     */
    public void toLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
}
