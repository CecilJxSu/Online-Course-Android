package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.MessageActivity;

@PerActivity
public class DeleteMessagePresenter implements Presenter {

    MessageActivity deleteMessageActivity;

    private final UseCase deleteMessageUseCase;

    @Inject
    public DeleteMessagePresenter(UseCase deleteMessageUseCase) {
        this.deleteMessageUseCase = deleteMessageUseCase;
    }

    public void setView(@NonNull MessageActivity deleteMessageActivity) {
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
                        DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("消息已经删除");
                        break;
                    case 401:
                        DeleteMessagePresenter.this.deleteMessageActivity.toLogin();
                        break;
                    default:
                        DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                DeleteMessagePresenter.this.deleteMessageActivity.showToastMessage("网络连接错误");
                e.printStackTrace();
            }
        }

        @Override
        public void onNext(Void empty) {
        }
    }
}