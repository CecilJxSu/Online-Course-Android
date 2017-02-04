package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        int index = 0;
        for(QuestionModel questionModel: questions) {
            questionModel.setQuestion(++index + "、" + questionModel.getQuestion());
        }
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
        if (view == null) {
            switch (questions.get(position).getType()) {
                case "判断题":
                    view = activity.getLayoutInflater().inflate(R.layout.question_true_false, null);
                    QuestionsTrueFalseViewHolder holder1;
                    holder1 = new QuestionsTrueFalseViewHolder(activity,view, questions.get(position));
                    view.setTag(holder1);
                    break;
                case "单选题":
                    view = activity.getLayoutInflater().inflate(R.layout.question_single_select, null);
                    QuestionsSingleSelectViewHolder holder2;
                    holder2 = new QuestionsSingleSelectViewHolder(activity,view, questions.get(position));
                    view.setTag(holder2);
                    break;
                case "多选题":
                    view = activity.getLayoutInflater().inflate(R.layout.question_single_select, null);
                    QuestionsMultiSelectViewHolder holder3;
                    holder3 = new QuestionsMultiSelectViewHolder(activity,view, questions.get(position));
                    view.setTag(holder3);
                    break;
                case "填空题":
                    view = activity.getLayoutInflater().inflate(R.layout.question_blank, null);
                    QuestionsBlankViewHolder holder4;
                    holder4 = new QuestionsBlankViewHolder(activity,view, questions.get(position));
                    view.setTag(holder4);
                    break;
                default:
                    view = new View(activity);
            }
        }

        return view;
    }
}
