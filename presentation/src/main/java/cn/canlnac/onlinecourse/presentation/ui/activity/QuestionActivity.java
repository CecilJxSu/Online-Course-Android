package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetQuestionComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetQuestionModule;
import cn.canlnac.onlinecourse.presentation.model.PaperModel;
import cn.canlnac.onlinecourse.presentation.model.QuestionListModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetQuestionPresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.QuestionsAdapter;
import cn.canlnac.onlinecourse.presentation.util.DensityUtil;

public class QuestionActivity extends BaseActivity {

    @BindView(R.id.question_type) LinearLayout questionContainer;
    @BindView(R.id.question_tag) TextView label;

    @Inject
    GetQuestionPresenter getQuestionPresenter;

    private int catalogId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        catalogId = getIntent().getIntExtra("catalogId", -1);
        if (catalogId == -1) {
            finish();
        }

        ButterKnife.bind(this);

        getQuestions();
    }

    /**
     * 返回按钮事件
     * @param v
     */
    @OnClick(R.id.question_close)
    public void onClickClose(View v) {
        finish();
    }

    /**
     * 获取小测
     */
    public void getQuestions() {
        //获取小测
        DaggerGetQuestionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .getQuestionModule(new GetQuestionModule(catalogId))
                .build().inject(this);

        getQuestionPresenter.setView(this);
        getQuestionPresenter.initialize();
    }

    public void showQuestions(QuestionListModel questions) {
        label.setText("满分：" + questions.getTotal());
        //显示问题
        int index = 0;
        for (PaperModel paperModel: questions.getQuestions()) {
            //添加题型标题
            TextView questionTitle = new TextView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            questionTitle.setPadding(DensityUtil.dp2px(this,10),DensityUtil.dp2px(this,5),DensityUtil.dp2px(this,10),DensityUtil.dp2px(this,5));
            questionTitle.setLayoutParams(layoutParams);
            questionTitle.setTextColor(0xFF333333);

            //添加问题
            ListView questionsView = new ListView(this);
            questionsView.setLayoutParams(layoutParams);

            QuestionsAdapter adapter = new QuestionsAdapter(this,paperModel.getQuestions());
            questionsView.setAdapter(adapter);
            //题号
            String[] indexStr = {"一、","二、","三、","四、","五、","六、","七、","八、","九、","十、"};
            int score = (int)paperModel.getScore();

            switch (paperModel.getType()) {
                case "判断题":

                    questionTitle.setText(indexStr[index++] + "判断题（共 " +
                            score * paperModel.getQuestions().size() +
                            " 分，每小题 "+ score +" 分）。");
                    questionContainer.addView(questionTitle);
                    questionContainer.addView(questionsView);
                    break;
                case "单选题":
                    questionTitle.setText(indexStr[index++] + "单选题（共 " +
                            score * paperModel.getQuestions().size() +
                            " 分，每小题 "+ score +" 分）。");
                    questionContainer.addView(questionTitle);
                    questionContainer.addView(questionsView);
                    break;
                case "多选题":

                    questionTitle.setText(indexStr[index++] + "多选题（共 " +
                            score * paperModel.getQuestions().size() +
                            " 分，每小题 "+ score +" 分）。");
                    questionContainer.addView(questionTitle);
                    questionContainer.addView(questionsView);
                    break;
                case "填空题":
                    questionTitle.setText(indexStr[index++] + "填空题（共 " +
                            score * paperModel.getQuestions().size() +
                            " 分，每小题 "+ score +" 分）。");
                    questionContainer.addView(questionTitle);
                    questionContainer.addView(questionsView);
                    break;
            }
        }
    }

    @OnClick(R.id.question_check)
    public void check (View v) {

    }
}
