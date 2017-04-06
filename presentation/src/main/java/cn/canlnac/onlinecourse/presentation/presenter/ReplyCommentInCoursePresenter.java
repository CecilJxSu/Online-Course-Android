package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostReplyInCourseFragment;

@PerActivity
public class ReplyCommentInCoursePresenter implements Presenter {

    PostReplyInCourseFragment replyCommentInCourseActivity;

    private final UseCase replyCommentInCourseUseCase;

    @Inject
    public ReplyCommentInCoursePresenter(UseCase replyCommentInCourseUseCase) {
        this.replyCommentInCourseUseCase = replyCommentInCourseUseCase;
    }

    public void setView(@NonNull PostReplyInCourseFragment replyCommentInCourseActivity) {
        this.replyCommentInCourseActivity = replyCommentInCourseActivity;
    }

    public void initialize() {
        this.replyCommentInCourseUseCase.execute(new ReplyCommentInCoursePresenter.ReplyCommentInCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.replyCommentInCourseUseCase.unsubscribe();
    }

    private final class ReplyCommentInCourseSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("评论或回复用户不存在");
                        break;
                    case 401:
                        ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.toLogin();
                        break;
                    default:
                        ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Integer replyId) {
            ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.postSuccess(replyId);
        }
    }
}