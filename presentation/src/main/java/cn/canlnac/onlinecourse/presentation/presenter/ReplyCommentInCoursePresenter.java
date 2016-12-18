package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class ReplyCommentInCoursePresenter implements Presenter {

    RegisterActivity replyCommentInCourseActivity;

    private final UseCase replyCommentInCourseUseCase;

    @Inject
    public ReplyCommentInCoursePresenter(UseCase replyCommentInCourseUseCase) {
        this.replyCommentInCourseUseCase = replyCommentInCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity replyCommentInCourseActivity) {
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
                        ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer answerId) {
            ReplyCommentInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("创建成功");
        }
    }
}