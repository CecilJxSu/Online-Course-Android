package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class LikeCommentPresenter implements Presenter {

    RegisterActivity likeCommentActivity;

    private final UseCase likeCommentUseCase;

    @Inject
    public LikeCommentPresenter(UseCase likeCommentUseCase) {
        this.likeCommentUseCase = likeCommentUseCase;
    }

    public void setView(@NonNull RegisterActivity likeCommentActivity) {
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
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        LikeCommentPresenter.this.likeCommentActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        LikeCommentPresenter.this.likeCommentActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        LikeCommentPresenter.this.likeCommentActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        LikeCommentPresenter.this.likeCommentActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                LikeCommentPresenter.this.likeCommentActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            LikeCommentPresenter.this.likeCommentActivity.showToastMessage("创建成功");
        }
    }
}