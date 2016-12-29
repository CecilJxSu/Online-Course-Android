package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CommentInChatViewHolder;

@PerActivity
public class LikeCommentInChatPresenter implements Presenter {

    CommentInChatViewHolder likeCommentActivity;

    private final UseCase likeCommentUseCase;

    @Inject
    public LikeCommentInChatPresenter(UseCase likeCommentUseCase) {
        this.likeCommentUseCase = likeCommentUseCase;
    }

    public void setView(@NonNull CommentInChatViewHolder likeCommentActivity) {
        this.likeCommentActivity = likeCommentActivity;
    }

    public void initialize() {
        this.likeCommentUseCase.execute(new LikeCommentInChatPresenter.LikeCommentSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.likeCommentUseCase.unsubscribe();
    }

    private final class LikeCommentSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException) e).code) {
                    case 304:
                        LikeCommentInChatPresenter.this.likeCommentActivity.showToastMessage("不能重复点赞");
                        break;
                    case 400:
                    case 404:
                        LikeCommentInChatPresenter.this.likeCommentActivity.showToastMessage("评论不存在");
                        break;
                    case 401:
                        LikeCommentInChatPresenter.this.likeCommentActivity.showToastMessage("未登陆");
                        LikeCommentInChatPresenter.this.likeCommentActivity.toLogin();
                        break;
                    default:
                        LikeCommentInChatPresenter.this.likeCommentActivity.showToastMessage("服务器错误:" + ((ResponseStatusException) e).code);
                    }
            }else{
                e.printStackTrace();
                LikeCommentInChatPresenter.this.likeCommentActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Void empty) {
            LikeCommentInChatPresenter.this.likeCommentActivity.changeLike(true);
            LikeCommentInChatPresenter.this.likeCommentActivity.changeLikeCount(true);
        }
    }
}