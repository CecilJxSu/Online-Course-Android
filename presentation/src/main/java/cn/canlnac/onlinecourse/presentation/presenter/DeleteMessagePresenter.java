package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class DeleteMessagePresenter implements Presenter {

    RegisterActivity deleteMessageActivity;

    private final UseCase deleteMessageUseCase;

    @Inject
    public DeleteMessagePresenter(UseCase deleteMessageUseCase) {
        this.deleteMessageUseCase = deleteMessageUseCase;
    }

    public void setView(@NonNull RegisterActivity deleteMessageActivity) {
        this.deleteMessageActivity = deleteMessageActivity;
    }

    public void initialize() {
        this.deleteMessageUseCase.execute(new DeleteMessagePresenter.DeleteMessageSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.deleteMessageUseCase.unsubscribe();
    }

    private final class DeleteMessageSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("创建成功");
        }
    }
}