package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.QuestionModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;

/**
 * 小测 view holder.
 */

public class QuestionsMultiSelectViewHolder implements QuestionViewHolder, View.OnClickListener {
    @BindView(R.id.question_title) TextView questionTitle;
    @BindView(R.id.question_answer) RadioGroup questionAnswer;
    @BindView(R.id.question_explain) TextView questionExplain;

    private Activity activity;

    List<Integer> currentChecked = new ArrayList<>();

    List<Integer> answerPosition = new ArrayList<>();

    public QuestionsMultiSelectViewHolder(Activity activity, View view, QuestionModel question) {
        ButterKnife.bind(this, view);

        questionTitle.setText(question.getQuestion());

        String[] keys = question.getItem().keySet().toArray(new String[question.getItem().size()]);

        for (int i = 0; i < keys.length; i++) {
            CheckBox checkBox = new CheckBox(activity);
            checkBox.setTag(i);
            checkBox.setText(keys[i] + "、" + question.getItem().get(keys[i]));
            questionAnswer.addView(checkBox);
            checkBox.setOnClickListener(this);

            for (String answer: question.getAnswer()) {
                if (keys[i].equals(answer)) {
                    answerPosition.add(i);
                }
            }
        }

        questionExplain.setText("答案解析：" +question.getAnswer().toString()+"\n"+question.getExplains());

        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        if (((CheckBox)v).isChecked()) {
            currentChecked.add((int)v.getTag());
        } else {
            currentChecked.remove((int)v.getTag());
        }
    }

    @Override
    public boolean checkAnswer() {
        questionExplain.setVisibility(View.VISIBLE);
        boolean isAllCorrect = currentChecked.size() == answerPosition.size();
        for (int i = 0; i < currentChecked.size(); i++) {
            boolean isCorrect = false;
            for (int j = 0; j < answerPosition.size(); j++) {
                if (currentChecked.get(i).equals(answerPosition.get(j))) {
                    isCorrect = true;
                    ((CheckBox)questionAnswer.findViewWithTag(currentChecked.get(i))).setTextColor(0xFF00FF00);
                    break;
                }
            }
            if (!isCorrect) {
                isAllCorrect = false;
                ((CheckBox)questionAnswer.findViewWithTag(currentChecked.get(i))).setTextColor(0xFFFF0000);
            }
        }

        return isAllCorrect;
    }

    /**
     * 显示消息
     * @param message   消息
     */
    public void showToastMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 未登陆用户需要登陆
     */
    public void toLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
}
