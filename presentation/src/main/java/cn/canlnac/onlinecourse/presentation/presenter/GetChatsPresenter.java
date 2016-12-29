package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.ChatList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.ChatListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment2;

@PerActivity
public class GetChatsPresenter implements Presenter {

    TabFragment2 getChatsActivity;

    private final UseCase getChatsUseCase;
    private final ChatListModelDataMapper chatListModelDataMapper;

    private int state;

    @Inject
    public GetChatsPresenter(UseCase getChatsUseCase,ChatListModelDataMapper chatListModelDataMapper) {
        this.getChatsUseCase = getChatsUseCase;
        this.chatListModelDataMapper = chatListModelDataMapper;
    }

    public void setView(@NonNull TabFragment2 getChatsActivity, int state) {
        this.getChatsActivity = getChatsActivity;
        this.state = state;
    }

    public void initialize() {
        this.getChatsUseCase.execute(new GetChatsPresenter.GetChatsSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getChatsUseCase.unsubscribe();
    }

    private final class GetChatsSubscriber extends DefaultSubscriber<ChatList> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        if (state == 0) {
                            GetChatsPresenter.this.getChatsActivity.showRefreshError("参数错误");
                        }
                        break;
                    case 404:
                        if (state == 0) {
                            GetChatsPresenter.this.getChatsActivity.showRefreshError("没有话题");
                        }
                        break;
                    default:
                        if (state == 0) {
                            GetChatsPresenter.this.getChatsActivity.showRefreshError("加载失败");
                        }
                        GetChatsPresenter.this.getChatsActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                if (state == 0) {
                    GetChatsPresenter.this.getChatsActivity.showRefreshError("加载失败");
                }
                GetChatsPresenter.this.getChatsActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(ChatList chatList) {
            if (state == 0) {
                GetChatsPresenter.this.getChatsActivity.showRefreshChats(chatListModelDataMapper.transform(chatList));
            } else if (state == 1) {
                GetChatsPresenter.this.getChatsActivity.showLoadMoreChats(chatListModelDataMapper.transform(chatList));
            }
        }
    }
}