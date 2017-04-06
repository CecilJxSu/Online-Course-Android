package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class DeleteCatalogPresenter implements Presenter {

    RegisterActivity deleteCatalogActivity;

    private final UseCase deleteCatalogUseCase;

    @Inject
    public DeleteCatalogPresenter(UseCase deleteCatalogUseCase) {
        this.deleteCatalogUseCase = deleteCatalogUseCase;
    }

    public void setView(@NonNull RegisterActivity deleteCatalogActivity) {
        this.deleteCatalogActivity = deleteCatalogActivity;
    }

    public void initialize() {
        this.deleteCatalogUseCase.execute(new DeleteCatalogPresenter.DeleteCatalogSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.deleteCatalogUseCase.unsubscribe();
    }

    private final class DeleteCatalogSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        DeleteCatalogPresenter.this.deleteCatalogActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        DeleteCatalogPresenter.this.deleteCatalogActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        DeleteCatalogPresenter.this.deleteCatalogActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        DeleteCatalogPresenter.this.deleteCatalogActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                DeleteCatalogPresenter.this.deleteCatalogActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            DeleteCatalogPresenter.this.deleteCatalogActivity.showToastMessage("创建成功");
        }
    }
}