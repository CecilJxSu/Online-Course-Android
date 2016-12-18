package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UpdateUserProfilePresenter implements Presenter {

    RegisterActivity updateUserProfileActivity;

    private final UseCase updateUserProfileUseCase;

    @Inject
    public UpdateUserProfilePresenter(UseCase updateUserProfileUseCase) {
        this.updateUserProfileUseCase = updateUserProfileUseCase;
    }

    public void setView(@NonNull RegisterActivity updateUserProfileActivity) {
        this.updateUserProfileActivity = updateUserProfileActivity;
    }

    public void initialize() {
        this.updateUserProfileUseCase.execute(new UpdateUserProfilePresenter.UpdateUserProfileSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.updateUserProfileUseCase.unsubscribe();
    }

    private final class UpdateUserProfileSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UpdateUserProfilePresenter.this.updateUserProfileActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UpdateUserProfilePresenter.this.updateUserProfileActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UpdateUserProfilePresenter.this.updateUserProfileActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UpdateUserProfilePresenter.this.updateUserProfileActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UpdateUserProfilePresenter.this.updateUserProfileActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UpdateUserProfilePresenter.this.updateUserProfileActivity.showToastMessage("创建成功");
        }
    }
}