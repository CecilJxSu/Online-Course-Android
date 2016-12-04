package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
            holder = new ViewHolder(activity,view, comments.get(position));
            view.setTag(holder);
        }

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.comment_head_icon) ImageView userIcon;
        @BindView(R.id.comment_user_name) TextView userName;
        @BindView(R.id.comment_content) TextView content;
        @BindView(R.id.comment_time) TextView postTime;
        @BindView(R.id.comment_like_count) TextView likeCount;
        @BindView(R.id.comment_reply) ImageView reply;
        @BindView(R.id.comment_like) ImageView like;
        @Nullable @BindView(R.id.comment_reply_list) ListView replyView;

        private boolean isLike;
        private boolean isReply;

        public ViewHolder(Activity activity, View view, CommentModel comment) {
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

            //回复评论
            if (comment.getReplies() != null && comment.getReplies().size() > 0) {
                ReplyAdapter adapter = new ReplyAdapter(activity, comment.getReplies());
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
