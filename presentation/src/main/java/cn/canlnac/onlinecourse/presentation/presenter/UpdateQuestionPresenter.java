package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UpdateQuestionPresenter implements Presenter {

    RegisterActivity updateQuestionActivity;

    private final UseCase updateQuestionUseCase;

    @Inject
    public UpdateQuestionPresenter(UseCase updateQuestionUseCase) {
        this.updateQuestionUseCase = updateQuestionUseCase;
    }

    public void setView(@NonNull RegisterActivity updateQuestionActivity) {
        this.updateQuestionActivity = updateQuestionActivity;
    }

    public void initialize() {
        this.updateQuestionUseCase.execute(new UpdateQuestionPresenter.UpdateQuestionSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.updateQuestionUseCase.unsubscribe();
    }

    private final class UpdateQuestionSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UpdateQuestionPresenter.this.updateQuestionActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UpdateQuestionPresenter.this.updateQuestionActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UpdateQuestionPresenter.this.updateQuestionActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UpdateQuestionPresenter.this.updateQuestionActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UpdateQuestionPresenter.this.updateQuestionActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UpdateQuestionPresenter.this.updateQuestionActivity.showToastMessage("创建成功");
        }
    }
}