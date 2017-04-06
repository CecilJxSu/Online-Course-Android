package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.DocumentListModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetDocumentsInCatalogPresenter implements Presenter {

    RegisterActivity getDocumentsInCatalogActivity;

    private final UseCase getDocumentsInCatalogUseCase;

    @Inject
    public GetDocumentsInCatalogPresenter(UseCase getDocumentsInCatalogUseCase) {
        this.getDocumentsInCatalogUseCase = getDocumentsInCatalogUseCase;
    }

    public void setView(@NonNull RegisterActivity getDocumentsInCatalogActivity) {
        this.getDocumentsInCatalogActivity = getDocumentsInCatalogActivity;
    }

    public void initialize() {
        this.getDocumentsInCatalogUseCase.execute(new GetDocumentsInCatalogPresenter.GetDocumentsInCatalogSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getDocumentsInCatalogUseCase.unsubscribe();
    }

    private final class GetDocumentsInCatalogSubscriber extends DefaultSubscriber<DocumentListModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetDocumentsInCatalogPresenter.this.getDocumentsInCatalogActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetDocumentsInCatalogPresenter.this.getDocumentsInCatalogActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetDocumentsInCatalogPresenter.this.getDocumentsInCatalogActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetDocumentsInCatalogPresenter.this.getDocumentsInCatalogActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetDocumentsInCatalogPresenter.this.getDocumentsInCatalogActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(DocumentListModel documentListModel) {
            GetDocumentsInCatalogPresenter.this.getDocumentsInCatalogActivity.showToastMessage("创建成功");
        }
    }
}