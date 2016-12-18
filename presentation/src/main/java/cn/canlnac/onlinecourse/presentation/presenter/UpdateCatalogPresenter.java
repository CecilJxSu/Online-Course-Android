package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UpdateCatalogPresenter implements Presenter {

    RegisterActivity updateCatalogActivity;

    private final UseCase updateCatalogUseCase;

    @Inject
    public UpdateCatalogPresenter(UseCase updateCatalogUseCase) {
        this.updateCatalogUseCase = updateCatalogUseCase;
    }

    public void setView(@NonNull RegisterActivity updateCatalogActivity) {
        this.updateCatalogActivity = updateCatalogActivity;
    }

    public void initialize() {
        this.updateCatalogUseCase.execute(new UpdateCatalogPresenter.UpdateCatalogSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.updateCatalogUseCase.unsubscribe();
    }

    private final class UpdateCatalogSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UpdateCatalogPresenter.this.updateCatalogActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UpdateCatalogPresenter.this.updateCatalogActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UpdateCatalogPresenter.this.updateCatalogActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UpdateCatalogPresenter.this.updateCatalogActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UpdateCatalogPresenter.this.updateCatalogActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UpdateCatalogPresenter.this.updateCatalogActivity.showToastMessage("创建成功");
        }
    }
}