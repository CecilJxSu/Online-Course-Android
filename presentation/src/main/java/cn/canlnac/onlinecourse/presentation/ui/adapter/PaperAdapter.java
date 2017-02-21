package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.QuestionModel;

/**
 * 小测适配器.
 */

public class PaperAdapter extends BaseAdapter {
    private Activity activity;
    private List<Map<String,Object>> papers;
    private List<Map<String,Object>> checkResult;
    private Map<Integer,View> views = new HashMap<>();

    public PaperAdapter(Activity activity, List<Map<String,Object>> papers, List<Map<String,Object>> checkResult) {
        this.activity = activity;
        this.papers = papers;
        this.checkResult = checkResult;
        checkResult.clear();
    }

    @Override
    public int getCount() {
        return papers==null ? 0 : papers.size();
    }
    @Override
    public Object getItem(int position) {
        return papers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = views.get(position);
        if (view != null) {
            return view;
        }

        Map<String,Object> paper = papers.get(position);

        switch (paper.get("type").toString()) {
            case "title":
                view = activity.getLayoutInflater().inflate(R.layout.paper_view, null);
                //添加题型标题
                TextView questionTitle = (TextView) view.findViewById(R.id.paper_view_title);
                questionTitle.setText(paper.get("title").toString());

                view.setOnClickListener(null);
                break;
            case "question":
                QuestionModel questions = (QuestionModel) paper.get("question");
                switch (questions.getType()) {
                    case "判断题":
                        view = activity.getLayoutInflater().inflate(R.layout.question_true_false, null);
                        QuestionsTrueFalseViewHolder holder1;
                        holder1 = new QuestionsTrueFalseViewHolder(activity,view, questions);
                        view.setTag(holder1);

                        //分数记录
                        Map<String,Object> result1 = new HashMap<>();
                        result1.put("view", holder1);
                        result1.put("score", paper.get("score"));
                        checkResult.add(result1);
                        break;
                    case "单选题":
                        view = activity.getLayoutInflater().inflate(R.layout.question_single_select, null);
                        QuestionsSingleSelectViewHolder holder2;
                        holder2 = new QuestionsSingleSelectViewHolder(activity,view, questions);
                        view.setTag(holder2);

                        //分数记录
                        Map<String,Object> result2 = new HashMap<>();
                        result2.put("view", holder2);
                        result2.put("score", paper.get("score"));
                        checkResult.add(result2);
                        break;
                    case "多选题":
                        view = activity.getLayoutInflater().inflate(R.layout.question_single_select, null);
                        QuestionsMultiSelectViewHolder holder3;
                        holder3 = new QuestionsMultiSelectViewHolder(activity,view, questions);
                        view.setTag(holder3);

                        //分数记录
                        Map<String,Object> result3 = new HashMap<>();
                        result3.put("view", holder3);
                        result3.put("score", paper.get("score"));
                        checkResult.add(result3);
                        break;
                    case "填空题":
                        view = activity.getLayoutInflater().inflate(R.layout.question_blank, null);
                        QuestionsBlankViewHolder holder4;
                        holder4 = new QuestionsBlankViewHolder(activity,view, questions);
                        view.setTag(holder4);

                        //分数记录
                        Map<String,Object> result4 = new HashMap<>();
                        result4.put("view", holder4);
                        result4.put("score", paper.get("score"));
                        checkResult.add(result4);
                        break;
                    default:
                        view = new View(activity);
                }
                break;
        }

        views.put(position,view);

        return view;
    }
}
