package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UnfollowUserPresenter implements Presenter {

    RegisterActivity unfollowUserActivity;

    private final UseCase unfollowUserUseCase;

    @Inject
    public UnfollowUserPresenter(UseCase unfollowUserUseCase) {
        this.unfollowUserUseCase = unfollowUserUseCase;
    }

    public void setView(@NonNull RegisterActivity unfollowUserActivity) {
        this.unfollowUserActivity = unfollowUserActivity;
    }

    public void initialize() {
        this.unfollowUserUseCase.execute(new UnfollowUserPresenter.UnfollowUserSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.unfollowUserUseCase.unsubscribe();
    }

    private final class UnfollowUserSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UnfollowUserPresenter.this.unfollowUserActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UnfollowUserPresenter.this.unfollowUserActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UnfollowUserPresenter.this.unfollowUserActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UnfollowUserPresenter.this.unfollowUserActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UnfollowUserPresenter.this.unfollowUserActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnfollowUserPresenter.this.unfollowUserActivity.showToastMessage("创建成功");
        }
    }
}