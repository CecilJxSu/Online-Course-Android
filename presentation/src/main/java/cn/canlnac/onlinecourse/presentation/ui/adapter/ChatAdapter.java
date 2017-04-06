package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;
import java.util.Locale;

import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.ChatModel;

/**
 * 话题适配器.
 */

public class ChatAdapter extends BaseAdapter {
    private Activity activity;
    private List<ChatModel> chats;
    static final PrettyTime prettyTime = new PrettyTime(Locale.CHINA);

    public ChatAdapter(Activity activity, List<ChatModel> chats) {
        this.activity = activity;
        this.chats = chats;
    }

    @Override
    public int getCount() {
        return chats==null ? 0 : chats.size();
    }
    @Override
    public Object getItem(int position) {
        return chats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ChatViewHolder holder;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.chat_list_view, null);
        }

        holder = new ChatViewHolder(activity,view, chats.get(position), prettyTime);
        view.setTag(holder);

        return view;
    }
}
