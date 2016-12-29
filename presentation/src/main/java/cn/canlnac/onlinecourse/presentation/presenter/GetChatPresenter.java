package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Chat;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.ChatModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.ChatActivity;

@PerActivity
public class GetChatPresenter implements Presenter {

    ChatActivity getChatActivity;

    private final UseCase getChatUseCase;
    private final ChatModelDataMapper chatModelDataMapper;

    @Inject
    public GetChatPresenter(UseCase getChatUseCase, ChatModelDataMapper chatModelDataMapper) {
        this.getChatUseCase = getChatUseCase;
        this.chatModelDataMapper = chatModelDataMapper;
    }

    public void setView(@NonNull ChatActivity getChatActivity) {
        this.getChatActivity = getChatActivity;
    }

    public void initialize() {
        this.getChatUseCase.execute(new GetChatPresenter.GetChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getChatUseCase.unsubscribe();
    }

    private final class GetChatSubscriber extends DefaultSubscriber<Chat> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetChatPresenter.this.getChatActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        GetChatPresenter.this.getChatActivity.showToastMessage("话题已删除");
                        GetChatPresenter.this.getChatActivity.onClickClose(null);
                        break;
                    case 401:
                        GetChatPresenter.this.getChatActivity.showToastMessage("未登陆");
                        GetChatPresenter.this.getChatActivity.toLogin();
                        break;
                    default:
                        GetChatPresenter.this.getChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetChatPresenter.this.getChatActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Chat chat) {
            GetChatPresenter.this.getChatActivity.showChat(chatModelDataMapper.transform(chat));
        }
    }
}