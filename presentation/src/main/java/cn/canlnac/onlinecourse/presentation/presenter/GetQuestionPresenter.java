package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.QuestionModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetQuestionPresenter implements Presenter {

    RegisterActivity getQuestionActivity;

    private final UseCase getQuestionUseCase;

    @Inject
    public GetQuestionPresenter(UseCase getQuestionUseCase) {
        this.getQuestionUseCase = getQuestionUseCase;
    }

    public void setView(@NonNull RegisterActivity getQuestionActivity) {
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

    private final class GetQuestionSubscriber extends DefaultSubscriber<QuestionModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetQuestionPresenter.this.getQuestionActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetQuestionPresenter.this.getQuestionActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetQuestionPresenter.this.getQuestionActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetQuestionPresenter.this.getQuestionActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetQuestionPresenter.this.getQuestionActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(QuestionModel questionModel) {
            GetQuestionPresenter.this.getQuestionActivity.showToastMessage("创建成功");
        }
    }
}