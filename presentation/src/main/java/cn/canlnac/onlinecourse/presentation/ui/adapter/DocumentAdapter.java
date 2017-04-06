package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;
import java.util.Locale;

import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.DocumentModel;

/**
 * 评论适配器.
 */

public class DocumentAdapter extends BaseAdapter {
    private Activity activity;
    private List<DocumentModel> documents;
    static final PrettyTime prettyTime = new PrettyTime(Locale.CHINA);

    public DocumentAdapter(Activity activity, List<DocumentModel> documents) {
        this.activity = activity;
        this.documents = documents;
    }

    @Override
    public int getCount() {
        return documents==null ? 0 : documents.size();
    }
    @Override
    public Object getItem(int position) {
        return documents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        DocumentViewHolder holder;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.documents, null);
        }

        holder = new DocumentViewHolder(activity,view, documents.get(position), prettyTime);
        view.setTag(holder);

        return view;
    }
}
