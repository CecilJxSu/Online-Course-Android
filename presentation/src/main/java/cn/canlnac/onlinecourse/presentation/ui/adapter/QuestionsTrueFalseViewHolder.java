package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
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

public class QuestionsTrueFalseViewHolder {
    @BindView(R.id.question_title) TextView questionTitle;

    private Activity activity;

    public QuestionsTrueFalseViewHolder(Activity activity, View view, QuestionModel question) {
        ButterKnife.bind(this, view);

        questionTitle.setText(question.getQuestion());

        this.activity = activity;
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