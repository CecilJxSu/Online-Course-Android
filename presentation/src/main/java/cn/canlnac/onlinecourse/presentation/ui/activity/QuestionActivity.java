package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetQuestionComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetQuestionModule;
import cn.canlnac.onlinecourse.presentation.model.QuestionListModel;
import cn.canlnac.onlinecourse.presentation.model.QuestionModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetQuestionPresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.QuestionsAdapter;

public class QuestionActivity extends BaseActivity {

    @BindView(R.id.question_list) ListView listView;

    private QuestionsAdapter adapter;
    private Handler handler;

    @Inject
    GetQuestionPresenter getQuestionPresenter;
    List<QuestionModel> questions = new ArrayList<>();
    private int totalScore = 0;

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

        handler = new Handler();

        adapter = new QuestionsAdapter(this, questions);
        listView.setAdapter(adapter);

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
        this.totalScore = questions.getTotal();
        this.questions.addAll(questions.getQuestions());
        adapter.notifyDataSetChanged();
    }
}
