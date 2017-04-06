package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.ChatViewHolder;

@PerActivity
public class LikeChatPresenter implements Presenter {

    ChatViewHolder likeChatActivity;

    private final UseCase likeChatUseCase;

    @Inject
    public LikeChatPresenter(UseCase likeChatUseCase) {
        this.likeChatUseCase = likeChatUseCase;
    }

    public void setView(@NonNull ChatViewHolder likeChatActivity) {
        this.likeChatActivity = likeChatActivity;
    }

    public void initialize() {
        this.likeChatUseCase.execute(new LikeChatPresenter.LikeChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.likeChatUseCase.unsubscribe();
    }

    private final class LikeChatSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 304:
                        LikeChatPresenter.this.likeChatActivity.showToastMessage("不能重复点赞");
                        break;
                    case 400:
                    case 404:
                        LikeChatPresenter.this.likeChatActivity.showToastMessage("话题不存在");
                        break;
                    case 401:
                        LikeChatPresenter.this.likeChatActivity.showToastMessage("未登陆");
                        LikeChatPresenter.this.likeChatActivity.toLogin();
                        break;
                    default:
                        LikeChatPresenter.this.likeChatActivity.showToastMessage("服务器错误:" + ((ResponseStatusException) e).code);
                }
            } else {
                e.printStackTrace();
                LikeChatPresenter.this.likeChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            LikeChatPresenter.this.likeChatActivity.changeLike(true);
            LikeChatPresenter.this.likeChatActivity.changeLikeCount(true);
        }
    }
}