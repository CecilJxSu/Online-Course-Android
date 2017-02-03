package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.List;

import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.QuestionModel;

/**
 * 小测适配器.
 */

public class QuestionsAdapter extends BaseAdapter {
    private Activity activity;
    private List<QuestionModel> questions;

    public QuestionsAdapter(Activity activity, List<QuestionModel> questions) {
        this.activity = activity;
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions==null ? 0 : questions.size();
    }
    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        QuestionsViewHolder holder;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.listitem_question, null);
        }

        holder = new QuestionsViewHolder(activity,view, questions.get(position));
        view.setTag(holder);

        return view;
    }
}
