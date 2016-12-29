package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CommentInChatViewHolder;

@PerActivity
public class UnlikeCommentInChatPresenter implements Presenter {

    CommentInChatViewHolder unlikeCommentActivity;

    private final UseCase unlikeCommentUseCase;

    @Inject
    public UnlikeCommentInChatPresenter(UseCase unlikeCommentUseCase) {
        this.unlikeCommentUseCase = unlikeCommentUseCase;
    }

    public void setView(@NonNull CommentInChatViewHolder unlikeCommentActivity) {
        this.unlikeCommentActivity = unlikeCommentActivity;
    }

    public void initialize() {
        this.unlikeCommentUseCase.execute(new UnlikeCommentInChatPresenter.UnlikeCommentSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.unlikeCommentUseCase.unsubscribe();
    }

    private final class UnlikeCommentSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                    case 404:
                        UnlikeCommentInChatPresenter.this.unlikeCommentActivity.showToastMessage("评论不存在");
                        break;
                    case 401:
                        UnlikeCommentInChatPresenter.this.unlikeCommentActivity.showToastMessage("未登陆");
                        UnlikeCommentInChatPresenter.this.unlikeCommentActivity.toLogin();
                        break;
                    default:
                        UnlikeCommentInChatPresenter.this.unlikeCommentActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                UnlikeCommentInChatPresenter.this.unlikeCommentActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnlikeCommentInChatPresenter.this.unlikeCommentActivity.changeLike(false);
            UnlikeCommentInChatPresenter.this.unlikeCommentActivity.changeLikeCount(false);
        }
    }
}