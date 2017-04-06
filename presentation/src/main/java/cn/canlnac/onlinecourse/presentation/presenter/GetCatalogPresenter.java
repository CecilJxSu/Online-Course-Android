package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CatalogModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetCatalogPresenter implements Presenter {

    RegisterActivity cetCatalogActivity;

    private final UseCase cetCatalogUseCase;

    @Inject
    public GetCatalogPresenter(UseCase cetCatalogUseCase) {
        this.cetCatalogUseCase = cetCatalogUseCase;
    }

    public void setView(@NonNull RegisterActivity cetCatalogActivity) {
        this.cetCatalogActivity = cetCatalogActivity;
    }

    public void initialize() {
        this.cetCatalogUseCase.execute(new GetCatalogPresenter.GetCatalogSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.cetCatalogUseCase.unsubscribe();
    }

    private final class GetCatalogSubscriber extends DefaultSubscriber<CatalogModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCatalogPresenter.this.cetCatalogActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetCatalogPresenter.this.cetCatalogActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetCatalogPresenter.this.cetCatalogActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetCatalogPresenter.this.cetCatalogActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetCatalogPresenter.this.cetCatalogActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(CatalogModel catalogModel) {
            GetCatalogPresenter.this.cetCatalogActivity.showToastMessage("创建成功");
        }
    }
}