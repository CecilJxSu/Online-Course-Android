package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;
import java.util.Locale;

import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.MessageModel;

/**
 * 消息适配器.
 */

public class MessageAdapter extends BaseAdapter {
    private Activity activity;
    private List<MessageModel> messages;
    static final PrettyTime prettyTime = new PrettyTime(Locale.CHINA);
    private boolean isRead;

    public MessageAdapter(Activity activity, List<MessageModel> messages, boolean isRead) {
        this.activity = activity;
        this.messages = messages;
        this.isRead = isRead;
    }

    @Override
    public int getCount() {
        return messages==null ? 0 : messages.size();
    }
    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        MessageViewHolder holder;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.view_message, null);
        }

        holder = new MessageViewHolder(activity,view, messages.get(position), isRead, prettyTime);
        view.setTag(holder);

        return view;
    }
}
