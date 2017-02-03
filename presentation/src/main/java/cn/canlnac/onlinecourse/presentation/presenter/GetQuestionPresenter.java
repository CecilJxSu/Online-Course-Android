package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.QuestionList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.QuestionListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.QuestionActivity;

@PerActivity
public class GetQuestionPresenter implements Presenter {

    QuestionActivity getQuestionActivity;

    private final UseCase getQuestionUseCase;
    private final QuestionListModelDataMapper questionListModelDataMapper;

    @Inject
    public GetQuestionPresenter(UseCase getQuestionUseCase, QuestionListModelDataMapper questionListModelDataMapper) {
        this.getQuestionUseCase = getQuestionUseCase;
        this.questionListModelDataMapper = questionListModelDataMapper;
    }

    public void setView(@NonNull QuestionActivity getQuestionActivity) {
        this.getQuestionActivity = getQuestionActivity;
    }

    public void initialize() {
        this.getQuestionUseCase.execute(new GetQuestionPresenter.GetQuestionSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getQuestionUseCase.unsubscribe();
    }

    private final class GetQuestionSubscriber extends DefaultSubscriber<QuestionList> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetQuestionPresenter.this.getQuestionActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        GetQuestionPresenter.this.getQuestionActivity.showToastMessage("该小节没有测验");
                        GetQuestionPresenter.this.getQuestionActivity.finish();
                        break;
                    case 401:
                        GetQuestionPresenter.this.getQuestionActivity.toLogin();
                        break;
                    default:
                        GetQuestionPresenter.this.getQuestionActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetQuestionPresenter.this.getQuestionActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(QuestionList questions) {
            GetQuestionPresenter.this.getQuestionActivity.showQuestions(questionListModelDataMapper.transform(questions));
        }
    }
}