package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class DeleteQuestionPresenter implements Presenter {

    RegisterActivity deleteQuestionActivity;

    private final UseCase deleteQuestionUseCase;

    @Inject
    public DeleteQuestionPresenter(UseCase deleteQuestionUseCase) {
        this.deleteQuestionUseCase = deleteQuestionUseCase;
    }

    public void setView(@NonNull RegisterActivity deleteQuestionActivity) {
        this.deleteQuestionActivity = deleteQuestionActivity;
    }

    public void initialize() {
        this.deleteQuestionUseCase.execute(new DeleteQuestionPresenter.DeleteQuestionSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.deleteQuestionUseCase.unsubscribe();
    }

    private final class DeleteQuestionSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        DeleteQuestionPresenter.this.deleteQuestionActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        DeleteQuestionPresenter.this.deleteQuestionActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        DeleteQuestionPresenter.this.deleteQuestionActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        DeleteQuestionPresenter.this.deleteQuestionActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                DeleteQuestionPresenter.this.deleteQuestionActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            DeleteQuestionPresenter.this.deleteQuestionActivity.showToastMessage("创建成功");
        }
    }
}