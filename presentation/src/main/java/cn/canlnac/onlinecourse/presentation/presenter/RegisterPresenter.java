package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

/**
 * 注册Presenter.
 */
@PerActivity
public class RegisterPresenter implements Presenter {

    RegisterActivity registerActivity;

    private final UseCase registerUseCase;

    @Inject
    public RegisterPresenter(UseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public void setView(@NonNull RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    public void initialize() {
        this.registerUseCase.execute(new RegisterSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.registerUseCase.unsubscribe();
    }

    private final class RegisterSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        RegisterPresenter.this.registerActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        RegisterPresenter.this.registerActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        RegisterPresenter.this.registerActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        RegisterPresenter.this.registerActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                RegisterPresenter.this.registerActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer userId) {
            RegisterPresenter.this.registerActivity.showToastMessage("注册成功");
        }
    }
}
