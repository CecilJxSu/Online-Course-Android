package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.LoginModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment3;

@PerActivity
public class SetLoginDataPresenter implements Presenter {

    TabFragment3 tabFragment3;

    private final UseCase loginUseCase;

    private LoginModelDataMapper loginModelDataMapper;

    @Inject
    public SetLoginDataPresenter(UseCase loginUseCase, LoginModelDataMapper loginModelDataMapper) {
        this.loginUseCase = loginUseCase;
        this.loginModelDataMapper = loginModelDataMapper;
    }

    public void setView(@NonNull TabFragment3 tabFragment3) {
        this.tabFragment3 = tabFragment3;
    }

    public void initialize() {
        this.loginUseCase.execute(new SetLoginDataPresenter.LoginSubscriber());
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

    private final class LoginSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            SetLoginDataPresenter.this.tabFragment3.showToastMessage("网络连接错误");
        }

        @Override
        public void onNext(Void empty) {
        }
    }
}