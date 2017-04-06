package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.ReplyModel;

/**
 * 回复评论适配器.
 */

public class MyRepliesReplyAdapter extends BaseAdapter {
    private Activity activity;
    private List<ReplyModel> replies;
    private PrettyTime prettyTime;

    public MyRepliesReplyAdapter(Activity activity, List<ReplyModel> replies, PrettyTime prettyTime) {
        this.activity = activity;
        this.replies = replies;
        this.prettyTime = prettyTime;
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
        MyRepliesReplyViewHolder holder;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.reply_to_comment, null);
        }

        holder = new MyRepliesReplyViewHolder(activity,view, replies.get(position), prettyTime);
        view.setTag(holder);

        return view;
    }
}
