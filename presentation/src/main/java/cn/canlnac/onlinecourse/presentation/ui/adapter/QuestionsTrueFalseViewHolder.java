package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.QuestionModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;

/**
 * 小测 view holder.
 */

public class QuestionsTrueFalseViewHolder implements QuestionViewHolder {
    @BindView(R.id.question_title) TextView questionTitle;
    @BindView(R.id.question_explain) TextView questionExplain;
    @BindView(R.id.question_answer) RadioGroup questionAnswer;

    String answer;

    private Activity activity;

    public QuestionsTrueFalseViewHolder(Activity activity, View view, QuestionModel question) {
        ButterKnife.bind(this, view);

        questionTitle.setText(question.getQuestion());

        answer = (question.getAnswer().get(0).equals("T"))?"对":"错";

        questionExplain.setText("答案解析：[" + answer +"]\n"+question.getExplains());

        this.activity = activity;
    }

    @Override
    public boolean checkAnswer() {
        questionExplain.setVisibility(View.VISIBLE);
        int id = questionAnswer.getCheckedRadioButtonId();
        if (id > 0) {
            RadioButton button = (RadioButton) questionAnswer.findViewById(id);
            if (button.getText().equals(answer)) {
                button.setTextColor(0xFF00FF00);
                return true;
            } else {
                button.setTextColor(0xFFFF0000);
            }
        }

        return false;
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
