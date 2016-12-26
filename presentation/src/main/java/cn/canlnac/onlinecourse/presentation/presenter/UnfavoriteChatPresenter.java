package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.ChatViewHolder;

@PerActivity
public class UnfavoriteChatPresenter implements Presenter {

    ChatViewHolder unfavoriteChatActivity;

    private final UseCase unfavoriteChatUseCase;

    @Inject
    public UnfavoriteChatPresenter(UseCase unfavoriteChatUseCase) {
        this.unfavoriteChatUseCase = unfavoriteChatUseCase;
    }

    public void setView(@NonNull ChatViewHolder unfavoriteChatActivity) {
        this.unfavoriteChatActivity = unfavoriteChatActivity;
    }

    public void initialize() {
        this.unfavoriteChatUseCase.execute(new UnfavoriteChatPresenter.UnfavoriteChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.unfavoriteChatUseCase.unsubscribe();
    }

    private final class UnfavoriteChatSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                    case 404:
                        UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("话题不存在");
                        break;
                    case 401:
                        UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("未登陆");
                        UnfavoriteChatPresenter.this.unfavoriteChatActivity.toLogin();
                        break;
                    default:
                        UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnfavoriteChatPresenter.this.unfavoriteChatActivity.changeFavorite(false);
            UnfavoriteChatPresenter.this.unfavoriteChatActivity.changeFavoriteCount(false);
        }
    }
}