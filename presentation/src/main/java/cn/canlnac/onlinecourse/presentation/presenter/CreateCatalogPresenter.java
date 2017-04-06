package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class CreateCatalogPresenter implements Presenter {

    RegisterActivity createCatalogActivity;

    private final UseCase createCatalogUseCase;

    @Inject
    public CreateCatalogPresenter(UseCase createCatalogUseCase) {
        this.createCatalogUseCase = createCatalogUseCase;
    }

    public void setView(@NonNull RegisterActivity createCatalogActivity) {
        this.createCatalogActivity = createCatalogActivity;
    }

    public void initialize() {
        this.createCatalogUseCase.execute(new CreateCatalogPresenter.CreateCatalogSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createCatalogUseCase.unsubscribe();
    }

    private final class CreateCatalogSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        CreateCatalogPresenter.this.createCatalogActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        CreateCatalogPresenter.this.createCatalogActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        CreateCatalogPresenter.this.createCatalogActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        CreateCatalogPresenter.this.createCatalogActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                CreateCatalogPresenter.this.createCatalogActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer catalogId) {
            CreateCatalogPresenter.this.createCatalogActivity.showToastMessage("注册成功");
        }
    }
}
