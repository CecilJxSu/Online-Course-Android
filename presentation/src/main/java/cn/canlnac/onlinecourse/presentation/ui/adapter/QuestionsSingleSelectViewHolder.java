package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
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

public class QuestionsSingleSelectViewHolder implements View.OnClickListener {
    @BindView(R.id.question_title) TextView questionTitle;
    @BindView(R.id.question_answer) RadioGroup questionAnswer;

    private Activity activity;

    int currentChecked = 0;

    public QuestionsSingleSelectViewHolder(Activity activity, View view, QuestionModel question) {
        ButterKnife.bind(this, view);

        questionTitle.setText(question.getQuestion());

        String[] keys = question.getItem().keySet().toArray(new String[question.getItem().size()]);

        int i = 0;
        for (String key : keys) {
            CheckBox checkBox = new CheckBox(activity);
            checkBox.setTag(i++);
            checkBox.setText(question.getItem().get(key));
            checkBox.setOnClickListener(this);
            questionAnswer.addView(checkBox);
        }

        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        ((CheckBox)questionAnswer.findViewWithTag(currentChecked)).setChecked(false);
        ((CheckBox)v).setChecked(true);
        currentChecked = (int)v.getTag();
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
