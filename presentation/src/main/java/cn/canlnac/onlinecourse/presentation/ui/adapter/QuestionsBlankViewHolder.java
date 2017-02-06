package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.QuestionModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;
import cn.canlnac.onlinecourse.presentation.util.DensityUtil;

/**
 * 小测 view holder.
 */

public class QuestionsBlankViewHolder implements QuestionViewHolder {
    @BindView(R.id.question_title) TextView questionTitle;
    @BindView(R.id.question_answer) LinearLayout questionAnswer;
    @BindView(R.id.question_explain) TextView questionExplain;

    List<String> answers = new ArrayList<>();

    private Activity activity;

    public QuestionsBlankViewHolder(Activity activity, View view, QuestionModel question) {
        ButterKnife.bind(this, view);

        questionTitle.setText(question.getQuestion());

        answers = question.getAnswer();

        int i = 0;
        for (String answer : question.getAnswer()) {
            EditText editText = new EditText(activity);
            editText.setTag(i);
            editText.setHint(++i + "、");

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    answer.length() * 3 * DensityUtil.dp2px(activity,15) + DensityUtil.dp2px(activity,15),
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            editText.setLayoutParams(layoutParams);
            editText.setMaxLines(1);
            questionAnswer.addView(editText);
        }

        questionExplain.setText("答案解析：" + question.getAnswer().toString() +"\n"+question.getExplains());

        this.activity = activity;
    }

    @Override
    public boolean checkAnswer() {
        questionExplain.setVisibility(View.VISIBLE);
        boolean isAllCorrect = true;

        for (int i = 0; i < answers.size(); i++) {
            String answer = ((EditText)questionAnswer.findViewWithTag(i)).getText().toString().trim();
            if (answer.equals(answers.get(i).trim())) {
                ((EditText)questionAnswer.findViewWithTag(i)).setTextColor(0xFF00FF00);
            } else {
                isAllCorrect = false;
                ((EditText)questionAnswer.findViewWithTag(i)).setTextColor(0xFFFF0000);
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
