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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;

/**
 * 评论 view holder.
 */

public class MyRepliesCommentViewHolder {
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

    public MyRepliesCommentViewHolder(Activity activity, View view, CommentModel comment, PrettyTime prettyTime) {
        ButterKnife.bind(this, view);

        this.activity = activity;

        userIcon.setImageURI(comment.getAuthor().getIconUrl());
        userName.setText(comment.getAuthor().getName());
        content.setText(comment.getContent());
        postTime.setText(prettyTime.format(new Date(comment.getDate())));
        likeCount.setText(comment.getLikeCount()+"");

        changeLike(comment.isLike());

        changeReply(comment.isReply());

        //回复评论
        MyRepliesReplyAdapter adapter = new MyRepliesReplyAdapter(activity, comment.getReplies(), prettyTime);
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {//Throws NullPointerException when invoke measure in low level android version
            View item = adapter.getView(i, null, replyView);
            if (item.getLayoutParams() == null) {
                item.setLayoutParams(new ViewGroup.LayoutParams(0,0));
            }

            item.measure(0,0);
            totalHeight += item.getMeasuredHeight();
        }
        replyView.setAdapter(adapter);
        ViewGroup.LayoutParams params = replyView.getLayoutParams();
        params.height = totalHeight + replyView.getDividerHeight() * (adapter.getCount()-1)+30;
        replyView.setLayoutParams(params);
        replyView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    /**
     * 改变点赞状态
     * @param isLike
     */
    public void changeLike(boolean isLike) {
        if (isLike) {
            like.setImageResource(R.drawable.thump_up_green_icon);
        } else {
            like.setImageResource(R.drawable.thump_up_icon);
        }
    }

    /**
     * 改变回复评论状态
     * @param isReply
     */
    public void changeReply(boolean isReply) {
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
