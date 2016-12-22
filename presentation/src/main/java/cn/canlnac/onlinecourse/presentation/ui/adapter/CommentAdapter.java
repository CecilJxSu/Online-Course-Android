package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;

/**
 * 评论适配器.
 */

public class CommentAdapter extends BaseAdapter {
    private Activity activity;
    private List<CommentModel> comments;
    static final PrettyTime prettyTime = new PrettyTime();

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
        @BindView(R.id.comment_head_icon) SimpleDraweeView userIcon;
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

            userIcon.setImageURI(comment.getAuthor().getIconUrl());
            userName.setText(comment.getAuthor().getName());
            content.setText(comment.getContent());
            postTime.setText(prettyTime.format(new Date(comment.getDate())));
            likeCount.setText(comment.getLikeCount()+"");

            changeLike(comment.isLike());

            changeReply(comment.isReply());

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

        @OnClick(R.id.comment_like)
        public void onClickLike(View v) {
            if (isLike) {
                changeLike(false);
            } else {
                changeLike(true);
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
    }
}
