package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class ReplyCommentInChatPresenter implements Presenter {

    RegisterActivity replyCommentInChatActivity;

    private final UseCase replyCommentInChatUseCase;

    @Inject
    public ReplyCommentInChatPresenter(UseCase replyCommentInChatUseCase) {
        this.replyCommentInChatUseCase = replyCommentInChatUseCase;
    }

    public void setView(@NonNull RegisterActivity replyCommentInChatActivity) {
        this.replyCommentInChatActivity = replyCommentInChatActivity;
    }

    public void initialize() {
        this.replyCommentInChatUseCase.execute(new ReplyCommentInChatPresenter.ReplyCommentInChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.replyCommentInChatUseCase.unsubscribe();
    }

    private final class ReplyCommentInChatSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        ReplyCommentInChatPresenter.this.replyCommentInChatActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        ReplyCommentInChatPresenter.this.replyCommentInChatActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        ReplyCommentInChatPresenter.this.replyCommentInChatActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        ReplyCommentInChatPresenter.this.replyCommentInChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                ReplyCommentInChatPresenter.this.replyCommentInChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer answerId) {
            ReplyCommentInChatPresenter.this.replyCommentInChatActivity.showToastMessage("创建成功");
        }
    }
}