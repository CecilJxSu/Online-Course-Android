package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CommentViewHolder;

@PerActivity
public class LikeCommentPresenter implements Presenter {

    CommentViewHolder likeCommentActivity;

    private final UseCase likeCommentUseCase;

    @Inject
    public LikeCommentPresenter(UseCase likeCommentUseCase) {
        this.likeCommentUseCase = likeCommentUseCase;
    }

    public void setView(@NonNull CommentViewHolder likeCommentActivity) {
        this.likeCommentActivity = likeCommentActivity;
    }

    public void initialize() {
        this.likeCommentUseCase.execute(new LikeCommentPresenter.LikeCommentSubscriber());
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
                        LikeCommentPresenter.this.likeCommentActivity.showToastMessage("不能重复点赞");
                        break;
                    case 400:
                    case 404:
                        LikeCommentPresenter.this.likeCommentActivity.showToastMessage("评论不存在");
                        break;
                    case 401:
                        LikeCommentPresenter.this.likeCommentActivity.showToastMessage("未登陆");
                        LikeCommentPresenter.this.likeCommentActivity.toLogin();
                        break;
                    default:
                        LikeCommentPresenter.this.likeCommentActivity.showToastMessage("服务器错误:" + ((ResponseStatusException) e).code);
                    }
            }else{
                e.printStackTrace();
                LikeCommentPresenter.this.likeCommentActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Void empty) {
            LikeCommentPresenter.this.likeCommentActivity.changeLike(true);
            LikeCommentPresenter.this.likeCommentActivity.changeLikeCount(true);
        }
    }
}