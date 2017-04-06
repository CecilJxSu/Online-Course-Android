package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.ChatViewHolder;

@PerActivity
public class UnlikeChatPresenter implements Presenter {

    ChatViewHolder unlikeChatActivity;

    private final UseCase unlikeChatUseCase;

    @Inject
    public UnlikeChatPresenter(UseCase unlikeChatUseCase) {
        this.unlikeChatUseCase = unlikeChatUseCase;
    }

    public void setView(@NonNull ChatViewHolder unlikeChatActivity) {
        this.unlikeChatActivity = unlikeChatActivity;
    }

    public void initialize() {
        this.unlikeChatUseCase.execute(new UnlikeChatPresenter.UnlikeChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.unlikeChatUseCase.unsubscribe();
    }

    private final class UnlikeChatSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                    case 404:
                        UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("话题不存在");
                        break;
                    case 401:
                        UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("未登陆");
                        UnlikeChatPresenter.this.unlikeChatActivity.toLogin();
                        break;
                    default:
                        UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnlikeChatPresenter.this.unlikeChatActivity.changeLike(false);
            UnlikeChatPresenter.this.unlikeChatActivity.changeLikeCount(false);
        }
    }
}