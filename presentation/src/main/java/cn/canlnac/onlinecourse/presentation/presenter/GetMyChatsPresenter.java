package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.ChatList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.ChatListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyChatActivity;

@PerActivity
public class GetMyChatsPresenter implements Presenter {

    MyChatActivity getMyChatsActivity;

    private int state;

    private final UseCase getMyChatsUseCase;
    private final ChatListModelDataMapper chatListModelDataMapper;

    @Inject
    public GetMyChatsPresenter(UseCase getMyChatsUseCase,ChatListModelDataMapper chatListModelDataMapper) {
        this.getMyChatsUseCase = getMyChatsUseCase;
        this.chatListModelDataMapper = chatListModelDataMapper;
    }

    /**
     * 设置View
     * @param getMyChatsActivity   view
     * @param state                0，刷新；1，加载更多
     */
    public void setView(@NonNull MyChatActivity getMyChatsActivity, int state) {
        this.getMyChatsActivity = getMyChatsActivity;
        this.state = state;
    }

    public void initialize() {
        this.getMyChatsUseCase.execute(new GetMyChatsPresenter.GetMyChatsSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getMyChatsUseCase.unsubscribe();
    }

    private final class GetMyChatsSubscriber extends DefaultSubscriber<ChatList> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        if (state == 0) {
                            GetMyChatsPresenter.this.getMyChatsActivity.showRefreshError("参数错误");
                        }
                        break;
                    case 404:
                        if (state == 0) {
                            GetMyChatsPresenter.this.getMyChatsActivity.showRefreshError("没有话题");
                        }
                        break;
                    case 401:
                        GetMyChatsPresenter.this.getMyChatsActivity.showRefreshError("加载失败");
                        GetMyChatsPresenter.this.getMyChatsActivity.toLogin();
                        break;
                    default:
                        if (state == 0) {
                            GetMyChatsPresenter.this.getMyChatsActivity.showRefreshError("加载失败");
                        }
                        GetMyChatsPresenter.this.getMyChatsActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                if (state == 0) {
                    GetMyChatsPresenter.this.getMyChatsActivity.showRefreshError("加载失败");
                }
                GetMyChatsPresenter.this.getMyChatsActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(ChatList chatList) {
            if (state == 0) {
                GetMyChatsPresenter.this.getMyChatsActivity.showRefreshChats(chatListModelDataMapper.transform(chatList));
            } else if (state == 1) {
                GetMyChatsPresenter.this.getMyChatsActivity.showLoadMoreChats(chatListModelDataMapper.transform(chatList));
            }
        }
    }
}