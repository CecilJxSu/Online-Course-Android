package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Login;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.LoginModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;

@PerActivity
public class LoginPresenter implements Presenter {

    LoginActivity loginActivity;

    private final UseCase loginUseCase;

    private LoginModelDataMapper loginModelDataMapper;

    @Inject
    public LoginPresenter(UseCase loginUseCase, LoginModelDataMapper loginModelDataMapper) {
        this.loginUseCase = loginUseCase;
        this.loginModelDataMapper = loginModelDataMapper;
    }

    public void setView(@NonNull LoginActivity loginActivity) {
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

    private final class LoginSubscriber extends DefaultSubscriber<Login> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        LoginPresenter.this.loginActivity.showToastMessage("用户名或密码输入错误");
                        break;
                    case 404:
                        LoginPresenter.this.loginActivity.showToastMessage("用户名或密码不正确");
                        break;
                    case 403:
                        LoginPresenter.this.loginActivity.showToastMessage("用户名或密码不正确");
                        break;
                    default:
                        LoginPresenter.this.loginActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                LoginPresenter.this.loginActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Login login) {
            LoginPresenter.this.loginActivity.showToastMessage("登陆成功");
            LoginPresenter.this.loginActivity.loginBack(loginModelDataMapper.transform(login));
        }
    }
}