package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.ReplyModel;

/**
 * 回复评论适配器.
 */

public class ReplyAdapter extends BaseAdapter {
    private Activity activity;
    private List<ReplyModel> replies;

    public ReplyAdapter(Activity activity, List<ReplyModel> replies) {
        this.activity = activity;
        this.replies = replies;
    }

    @Override
    public int getCount() {
        return replies==null ? 0 : replies.size();
    }
    @Override
    public Object getItem(int position) {
        return replies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = activity.getLayoutInflater().inflate(R.layout.reply_to_comment, null);
            holder = new ViewHolder(activity,view, replies.get(position));
            view.setTag(holder);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.reply_comment_username) TextView userName;
        @BindView(R.id.reply_comment_content) TextView content;
        @BindView(R.id.reply_comment_postTime) TextView postTime;
        @BindView(R.id.reply_comment_reply) ImageView replyImageView;

        private boolean isReply;

        public ViewHolder(Activity activity,View view, ReplyModel reply) {
            ButterKnife.bind(this, view);

            /*userName.setText(reply.getUserName() + ":");

            StringBuilder prefixContent = new StringBuilder();
            if (reply.getUserName() != null && !reply.getUserName().isEmpty()) {
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

            if (reply.getToUserName() != null && !reply.getToUserName().isEmpty()) {
                prefixContent.append("回复: @");
                prefixContent.append(reply.getToUserName());
                prefixContent.append('\u2002');
                prefixContent.append(reply.getContent());
            } else {
                prefixContent.append(reply.getContent());
            }
            content.setText(prefixContent);
            postTime.setText(reply.getPostTime());

            isReply = reply.isReply();
            if (isReply) {
                replyImageView.setImageResource(R.drawable.comment_green_icon);
            } else {
                replyImageView.setImageResource(R.drawable.comment_icon);
            }*/
        }
    }
}
