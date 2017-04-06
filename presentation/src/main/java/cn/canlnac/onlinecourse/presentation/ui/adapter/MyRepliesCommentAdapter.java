package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;
import java.util.Locale;

import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;

/**
 * 评论适配器.
 */

public class MyRepliesCommentAdapter extends BaseAdapter {
    private Activity activity;
    private List<CommentModel> comments;
    static final PrettyTime prettyTime = new PrettyTime(Locale.CHINA);

    public MyRepliesCommentAdapter(Activity activity, List<CommentModel> comments) {
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
        MyRepliesCommentViewHolder holder;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.comment_content, null);
        }

        holder = new MyRepliesCommentViewHolder(activity,view, comments.get(position), prettyTime);
        view.setTag(holder);

        return view;
    }
}
