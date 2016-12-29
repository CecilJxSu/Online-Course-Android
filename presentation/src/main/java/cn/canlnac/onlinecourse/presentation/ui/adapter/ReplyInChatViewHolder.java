package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.ReplyModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.CommentActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;

/**
 * 回复 view holder.
 */

public class ReplyInChatViewHolder {
    @BindView(R.id.reply_comment_username) TextView userName;
    @BindView(R.id.reply_comment_content) TextView content;
    @BindView(R.id.reply_comment_postTime) TextView postTime;
    @BindView(R.id.reply_comment_reply) ImageView replyImageView;

    private CommentActivity activity;

    private int commentId;
    private int toUserId;
    private String toUserName;

    public ReplyInChatViewHolder(Activity activity, View view, int commentId, ReplyModel reply, PrettyTime prettyTime) {
        ButterKnife.bind(this, view);

        this.activity = (CommentActivity)activity;

        this.commentId = commentId;
        toUserId = reply.getToUser().getId();
        toUserName = reply.getToUser().getName();

        userName.setText(reply.getAuthor().getName() + ":");

        StringBuilder prefixContent = new StringBuilder();
        if (reply.getAuthor().getName() != null && !reply.getAuthor().getName().isEmpty()) {
            char[] chars = userName.getText().toString().toCharArray();
            for (char _char:chars) {
                if (_char < 0x007F) {
                    prefixContent.append("\u2002");
                } else if (_char < 0x07FF) {
                    prefixContent.append("\u2002");
                } else {
                    prefixContent.append("\u2003");
                }
            }
        }

        if (reply.getToUser().getName() != null && !reply.getToUser().getName().isEmpty()) {
            prefixContent.append("回复: @");
            prefixContent.append(reply.getToUser().getName());
            prefixContent.append('\u2002');
            prefixContent.append(reply.getContent());
        } else {
            prefixContent.append(reply.getContent());
        }
        content.setText(prefixContent);
        postTime.setText(prettyTime.format(new Date(reply.getDate())));
    }

    @OnClick(R.id.reply_comment_reply)
    public void onClickReply(View view) {
        activity.toggleReplyFragment(commentId,toUserId,toUserName);
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
