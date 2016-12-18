package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CatalogModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetCatalogsPresenter implements Presenter {

    RegisterActivity getCatalogsActivity;

    private final UseCase getCatalogsUseCase;

    @Inject
    public GetCatalogsPresenter(UseCase getCatalogsUseCase) {
        this.getCatalogsUseCase = getCatalogsUseCase;
    }

    public void setView(@NonNull RegisterActivity getCatalogsActivity) {
        this.getCatalogsActivity = getCatalogsActivity;
    }

    public void initialize() {
        this.getCatalogsUseCase.execute(new GetCatalogsPresenter.GetCatalogsSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCatalogsUseCase.unsubscribe();
    }

    private final class GetCatalogsSubscriber extends DefaultSubscriber<List<CatalogModel>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(List<CatalogModel> catalogModelList) {
            GetCatalogsPresenter.this.getCatalogsActivity.showToastMessage("创建成功");
        }
    }
}