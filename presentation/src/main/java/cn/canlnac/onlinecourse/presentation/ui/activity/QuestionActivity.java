package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetQuestionComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetQuestionModule;
import cn.canlnac.onlinecourse.presentation.model.PaperModel;
import cn.canlnac.onlinecourse.presentation.model.QuestionListModel;
import cn.canlnac.onlinecourse.presentation.model.QuestionModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetQuestionPresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.PaperAdapter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.QuestionViewHolder;

public class QuestionActivity extends BaseActivity {

    @BindView(R.id.question_type) ListView questionContainer;
    @BindView(R.id.question_tag) TextView label;

    @Inject
    GetQuestionPresenter getQuestionPresenter;

    List<Map<String,Object>> checkResult = new ArrayList<>();

    private int catalogId;
    private float totalScore = 0;

    //题号
    private String[] indexStr = {"一、","二、","三、","四、","五、","六、","七、","八、","九、","十、"};

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
        totalScore = questions.getTotal();
        label.setText("满分：" + (int)totalScore);

        List<Map<String,Object>> papers = new ArrayList<>();

        int titleIndex = 0;
        for (PaperModel paperModel:questions.getQuestions()) { //处理标题
            String title;
            int score = (int)paperModel.getScore();
            switch (paperModel.getType()) {
                case "判断题":
                    title = indexStr[titleIndex++] + "判断题（共 " +
                            score * paperModel.getQuestions().size() +
                            " 分，每小题 "+ score +" 分）。";
                    break;
                case "单选题":
                    title = indexStr[titleIndex++] + "单选题（共 " +
                            score * paperModel.getQuestions().size() +
                            " 分，每小题 "+ score +" 分）。";
                    break;
                case "多选题":
                    title = indexStr[titleIndex++] + "多选题（共 " +
                            score * paperModel.getQuestions().size() +
                            " 分，每小题 "+ score +" 分）。";
                    break;
                case "填空题":
                    title = indexStr[titleIndex++] + "填空题（共 " +
                            score * paperModel.getQuestions().size() +
                            " 分，每小题 "+ score +" 分）。";
                    break;
                default:
                    title = "";
            }

            Map<String,Object> titleObj = new HashMap<>();
            titleObj.put("type","title");
            titleObj.put("title",title);
            papers.add(titleObj);

            int questionIndex = 0;
            for (QuestionModel questionModel: paperModel.getQuestions()) {  //处理小题
                questionModel.setQuestion(++questionIndex + "、" + questionModel.getQuestion());

                Map<String,Object> questionObj = new HashMap<>();
                questionObj.put("type","question");
                questionObj.put("question",questionModel);
                questionObj.put("score",paperModel.getScore());
                papers.add(questionObj);
            }
        }

        PaperAdapter paperAdapter = new PaperAdapter(this, papers, checkResult);
        questionContainer.setAdapter(paperAdapter);
    }

    @OnClick(R.id.question_check)
    public void check (View v) {
        float totalScore = 0;
        for (Map<String,Object> result: checkResult) {
            QuestionViewHolder view = (QuestionViewHolder)result.get("view");
            if (view != null) {
                if (view.checkAnswer()) {
                    totalScore += (float)result.get("score");
                }
            }
        }

        label.setText("满分：" + (int)this.totalScore + "，正确："+ (int)totalScore);
    }
}
