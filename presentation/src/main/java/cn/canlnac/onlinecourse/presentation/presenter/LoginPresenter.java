package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.LoginModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class LoginPresenter implements Presenter {

    RegisterActivity loginActivity;

    private final UseCase loginUseCase;

    @Inject
    public LoginPresenter(UseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void setView(@NonNull RegisterActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void initialize() {
        this.loginUseCase.execute(new LoginPresenter.LoginSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.loginUseCase.unsubscribe();
    }

    private final class LoginSubscriber extends DefaultSubscriber<LoginModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        LoginPresenter.this.loginActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        LoginPresenter.this.loginActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        LoginPresenter.this.loginActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        LoginPresenter.this.loginActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                LoginPresenter.this.loginActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(LoginModel loginModel) {
            LoginPresenter.this.loginActivity.showToastMessage("创建成功");
        }
    }
}