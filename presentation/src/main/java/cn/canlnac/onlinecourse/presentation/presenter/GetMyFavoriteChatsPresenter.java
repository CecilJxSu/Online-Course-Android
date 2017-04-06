package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.ChatList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.ChatListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyFavoriteActivity;

@PerActivity
public class GetMyFavoriteChatsPresenter implements Presenter {

    MyFavoriteActivity myFavoriteActivity;

    private int state;

    private final UseCase getMyChatsUseCase;
    private final ChatListModelDataMapper chatListModelDataMapper;

    @Inject
    public GetMyFavoriteChatsPresenter(UseCase getMyChatsUseCase, ChatListModelDataMapper chatListModelDataMapper) {
        this.getMyChatsUseCase = getMyChatsUseCase;
        this.chatListModelDataMapper = chatListModelDataMapper;
    }

    /**
     * 设置View
     * @param myFavoriteActivity   view
     * @param state                0，刷新；1，加载更多
     */
    public void setView(@NonNull MyFavoriteActivity myFavoriteActivity, int state) {
        this.myFavoriteActivity = myFavoriteActivity;
        this.state = state;
    }

    public void initialize() {
        this.getMyChatsUseCase.execute(new GetMyFavoriteChatsPresenter.GetMyChatsSubscriber());
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
                            GetMyFavoriteChatsPresenter.this.myFavoriteActivity.showRefreshError("参数错误");
                        }
                        break;
                    case 404:
                        if (state == 0) {
                            GetMyFavoriteChatsPresenter.this.myFavoriteActivity.showRefreshError("没有话题");
                        }
                        break;
                    case 401:
                        GetMyFavoriteChatsPresenter.this.myFavoriteActivity.showRefreshError("加载失败");
                        GetMyFavoriteChatsPresenter.this.myFavoriteActivity.toLogin();
                        break;
                    default:
                        if (state == 0) {
                            GetMyFavoriteChatsPresenter.this.myFavoriteActivity.showRefreshError("加载失败");
                        }
                        GetMyFavoriteChatsPresenter.this.myFavoriteActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                if (state == 0) {
                    GetMyFavoriteChatsPresenter.this.myFavoriteActivity.showRefreshError("加载失败");
                }
                GetMyFavoriteChatsPresenter.this.myFavoriteActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(ChatList chatList) {
            if (state == 0) {
                GetMyFavoriteChatsPresenter.this.myFavoriteActivity.showRefreshChats(chatListModelDataMapper.transform(chatList));
            } else if (state == 1) {
                GetMyFavoriteChatsPresenter.this.myFavoriteActivity.showLoadMoreChats(chatListModelDataMapper.transform(chatList));
            }
        }
    }
}