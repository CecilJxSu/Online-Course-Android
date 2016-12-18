package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UnlikeCommentPresenter implements Presenter {

    RegisterActivity unlikeCommentActivity;

    private final UseCase unlikeCommentUseCase;

    @Inject
    public UnlikeCommentPresenter(UseCase unlikeCommentUseCase) {
        this.unlikeCommentUseCase = unlikeCommentUseCase;
    }

    public void setView(@NonNull RegisterActivity unlikeCommentActivity) {
        this.unlikeCommentActivity = unlikeCommentActivity;
    }

    public void initialize() {
        this.unlikeCommentUseCase.execute(new UnlikeCommentPresenter.UnlikeCommentSubscriber());
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
                        UnlikeCommentPresenter.this.unlikeCommentActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UnlikeCommentPresenter.this.unlikeCommentActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UnlikeCommentPresenter.this.unlikeCommentActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UnlikeCommentPresenter.this.unlikeCommentActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UnlikeCommentPresenter.this.unlikeCommentActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnlikeCommentPresenter.this.unlikeCommentActivity.showToastMessage("创建成功");
        }
    }
}