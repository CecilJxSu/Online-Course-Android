package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;

/**
 * 评论适配器.
 */

public class CommentAdapter extends BaseAdapter {
    private Activity activity;
    private List<CommentModel> comments;

    public CommentAdapter(Activity activity, List<CommentModel> comments) {
        this.activity = activity;
        this.comments = comments;
    }

    @Override
    public int getCount() {
        return comments==null ? 0 : comments.size();
    }
    @Override
    public Object getItem(int position) {
        return comments.get(position);
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
            view = activity.getLayoutInflater().inflate(R.layout.comment_content, null);
            holder = new ViewHolder(view, comments.get(position));
            view.setTag(holder);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.comment_head_icon)
        ImageView userIcon;
        @BindView(R.id.comment_user_name)
        TextView userName;
        @BindView(R.id.comment_content) TextView content;
        @BindView(R.id.comment_time) TextView postTime;
        @BindView(R.id.comment_like_count) TextView likeCount;
        @BindView(R.id.comment_reply) ImageView reply;
        @BindView(R.id.comment_like) ImageView like;

        private boolean isLike;
        private boolean isReply;

        public ViewHolder(View view, CommentModel comment) {
            ButterKnife.bind(this, view);

            userIcon.setImageResource(comment.getUserIcon());
            userName.setText(comment.getUserName());
            content.setText(comment.getContent());
            postTime.setText(comment.getPostTime());
            likeCount.setText(comment.getLikeCount());

            isLike = comment.isLike();
            isReply = comment.isReply();
            if (isLike) {
                like.setImageResource(R.drawable.thump_up_green_icon);
            } else {
                like.setImageResource(R.drawable.thump_up_icon);
            }
            if (isReply) {
                reply.setImageResource(R.drawable.comment_green_icon);
            } else {
                reply.setImageResource(R.drawable.comment_icon);
            }
        }

        @OnTouch(R.id.comment_like)
        public boolean onTouch(View v, MotionEvent event) {
            if (isLike) {
                isLike = false;
                like.setImageResource(R.drawable.thump_up_icon);
            } else {
                isLike = true;
                like.setImageResource(R.drawable.thump_up_green_icon);
            }
            return false;
        }
    }
}
