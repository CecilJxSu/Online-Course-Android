package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Message;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.MessageActivity;

@PerActivity
public class GetMessagePresenter implements Presenter {

    MessageActivity getMessageActivity;

    private final UseCase getMessageUseCase;

    @Inject
    public GetMessagePresenter(UseCase getMessageUseCase) {
        this.getMessageUseCase = getMessageUseCase;
    }

    public void setView(@NonNull MessageActivity getMessageActivity) {
        this.getMessageActivity = getMessageActivity;
    }

    public void initialize() {
        this.getMessageUseCase.execute(new GetMessagePresenter.GetMessageSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getMessageUseCase.unsubscribe();
    }

    private final class GetMessageSubscriber extends DefaultSubscriber<Message> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetMessagePresenter.this.getMessageActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        GetMessagePresenter.this.getMessageActivity.showToastMessage("消息不存在");
                        break;
                    case 401:
                        GetMessagePresenter.this.getMessageActivity.toLogin();
                        break;
                    default:
                        GetMessagePresenter.this.getMessageActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetMessagePresenter.this.getMessageActivity.showToastMessage("网络连接错误");
                e.printStackTrace();
            }
        }

        @Override
        public void onNext(Message message) {
            GetMessagePresenter.this.getMessageActivity.readMessage();
        }
    }
}