package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class FollowUserPresenter implements Presenter {

    RegisterActivity followUserActivity;

    private final UseCase followUserUseCase;

    @Inject
    public FollowUserPresenter(UseCase followUserUseCase) {
        this.followUserUseCase = followUserUseCase;
    }

    public void setView(@NonNull RegisterActivity followUserActivity) {
        this.followUserActivity = followUserActivity;
    }

    public void initialize() {
        this.followUserUseCase.execute(new FollowUserPresenter.FollowUserSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.followUserUseCase.unsubscribe();
    }

    private final class FollowUserSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        FollowUserPresenter.this.followUserActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        FollowUserPresenter.this.followUserActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        FollowUserPresenter.this.followUserActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        FollowUserPresenter.this.followUserActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                FollowUserPresenter.this.followUserActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            FollowUserPresenter.this.followUserActivity.showToastMessage("创建成功");
        }
    }
}