package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.MessageList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.MessageListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.MessageActivity;

@PerActivity
public class GetMessagesPresenter implements Presenter {

    MessageActivity getMessagesActivity;
    private int state;
    private boolean isRead;

    private final UseCase getMessagesUseCase;
    private MessageListModelDataMapper messageListModelDataMapper;

    @Inject
    public GetMessagesPresenter(UseCase getMessagesUseCase, MessageListModelDataMapper messageListModelDataMapper) {
        this.getMessagesUseCase = getMessagesUseCase;
        this.messageListModelDataMapper = messageListModelDataMapper;
    }

    /**
     * 设置View
     * @param getMessagesActivity  view
     * @param state                0，刷新；1，加载更多
     */
    public void setView(@NonNull MessageActivity getMessagesActivity, int state, boolean isRead) {
        this.getMessagesActivity = getMessagesActivity;
        this.state = state;
        this.isRead = isRead;
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
                        if (state == 0) {
                            GetMessagesPresenter.this.getMessagesActivity.showRefreshError("参数错误");
                        }
                        break;
                    case 404:
                        if (state == 0) {
                            GetMessagesPresenter.this.getMessagesActivity.showRefreshError("没有消息");
                        }
                        break;
                    case 401:
                        break;
                    default:
                        GetMessagesPresenter.this.getMessagesActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                        if (state == 0) {
                            GetMessagesPresenter.this.getMessagesActivity.showRefreshError("服务器错误");
                        }
                }
            } else {
                GetMessagesPresenter.this.getMessagesActivity.showToastMessage("网络连接错误");
                if (state == 0) {
                    GetMessagesPresenter.this.getMessagesActivity.showRefreshError("网络连接错误");
                }
                e.printStackTrace();
            }
        }

        @Override
        public void onNext(MessageList messageList) {
            if (state == 0) {
                GetMessagesPresenter.this.getMessagesActivity.showRefreshMessages(messageListModelDataMapper.transform(messageList), isRead);
            } else if (state == 1) {
                GetMessagesPresenter.this.getMessagesActivity.showLoadMoreMessages(messageListModelDataMapper.transform(messageList), isRead);
            }
        }
    }
}