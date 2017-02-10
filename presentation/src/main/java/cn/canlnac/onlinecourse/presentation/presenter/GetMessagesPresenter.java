package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.MessageList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.MessageListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.MainActivity;

@PerActivity
public class GetMessagesPresenter implements Presenter {

    MainActivity getMessagesActivity;

    private final UseCase getMessagesUseCase;
    private MessageListModelDataMapper messageListModelDataMapper;

    @Inject
    public GetMessagesPresenter(UseCase getMessagesUseCase, MessageListModelDataMapper messageListModelDataMapper) {
        this.getMessagesUseCase = getMessagesUseCase;
        this.messageListModelDataMapper = messageListModelDataMapper;
    }

    public void setView(@NonNull MainActivity getMessagesActivity) {
        this.getMessagesActivity = getMessagesActivity;
    }

    public void initialize() {
        this.getMessagesUseCase.execute(new GetMessagesPresenter.GetMessagesSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getMessagesUseCase.unsubscribe();
    }

    private final class GetMessagesSubscriber extends DefaultSubscriber<MessageList> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetMessagesPresenter.this.getMessagesActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        break;
                    case 401:
                        break;
                    default:
                        GetMessagesPresenter.this.getMessagesActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetMessagesPresenter.this.getMessagesActivity.showToastMessage("网络连接错误");
                e.printStackTrace();
            }
        }

        @Override
        public void onNext(MessageList messageList) {
            GetMessagesPresenter.this.getMessagesActivity.showMessages(messageListModelDataMapper.transform(messageList));
        }
    }
}