package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Register;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.mapper.RegisterModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.RegisterFragment;

/**
 * 注册Presenter.
 */

public class RegisterPresenter implements Presenter {

    RegisterFragment registerFragment;

    private final UseCase registerUseCase;
    private final RegisterModelDataMapper registerModelDataMapper;

    @Inject
    public RegisterPresenter(UseCase registerUseCase, RegisterModelDataMapper registerModelDataMapper) {
        this.registerUseCase = registerUseCase;
        this.registerModelDataMapper = registerModelDataMapper;
    }

    public void setView(@NonNull RegisterFragment registerFragment) {
        this.registerFragment = registerFragment;
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
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            switch (((ResponseStatusException)e).code) {

            }
            RegisterPresenter.this.registerFragment.showError("");
        }

        @Override
        public void onNext(Register register) {
            RegisterPresenter.this.registerFragment.showError("注册成功");
        }
    }
}
