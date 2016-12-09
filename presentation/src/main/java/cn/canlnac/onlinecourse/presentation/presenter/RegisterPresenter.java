package cn.canlnac.onlinecourse.presentation.presenter;

import javax.inject.Inject;
import javax.inject.Named;

import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.mapper.RegisterModelDataMapper;

/**
 * 注册Presenter.
 */

public class RegisterPresenter implements Presenter {

    private final UseCase registerUseCase;
    private final RegisterModelDataMapper registerModelDataMapper;

    @Inject
    public RegisterPresenter(UseCase registerUseCase, RegisterModelDataMapper registerModelDataMapper) {
        this.registerUseCase = registerUseCase;
        this.registerModelDataMapper = registerModelDataMapper;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destory() {
        this.registerUseCase.unsubscribe();
    }
}
