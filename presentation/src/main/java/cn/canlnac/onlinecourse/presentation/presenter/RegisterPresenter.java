package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Register;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.mapper.RegisterModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

/**
 * 注册Presenter.
 */

public class RegisterPresenter implements Presenter {

    RegisterActivity registerActivity;

    private final UseCase registerUseCase;
    private final RegisterModelDataMapper registerModelDataMapper;

    @Inject
    public RegisterPresenter(UseCase registerUseCase, RegisterModelDataMapper registerModelDataMapper) {
        this.registerUseCase = registerUseCase;
        this.registerModelDataMapper = registerModelDataMapper;
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

    private final class RegisterSubscriber extends DefaultSubscriber<Register> {
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
                        RegisterPresenter.this.registerActivity.showToastMessage("服务器错误！");
                }
            } else {
                RegisterPresenter.this.registerActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Register register) {
            RegisterPresenter.this.registerActivity.showToastMessage("注册成功");
        }
    }
}
