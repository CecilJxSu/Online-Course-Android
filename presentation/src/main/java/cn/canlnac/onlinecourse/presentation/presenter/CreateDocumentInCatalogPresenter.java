package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class CreateDocumentInCatalogPresenter implements Presenter {

    RegisterActivity createDocumentInCatalogActivity;

    private final UseCase createDocumentInCatalogUseCase;

    @Inject
    public CreateDocumentInCatalogPresenter(UseCase createDocumentInCatalogUseCase) {
        this.createDocumentInCatalogUseCase = createDocumentInCatalogUseCase;
    }

    public void setView(@NonNull RegisterActivity createDocumentInCatalogActivity) {
        this.createDocumentInCatalogActivity = createDocumentInCatalogActivity;
    }

    public void initialize() {
        this.createDocumentInCatalogUseCase.execute(new CreateDocumentInCatalogPresenter.CreateDocumentInCatalogSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createDocumentInCatalogUseCase.unsubscribe();
    }

    private final class CreateDocumentInCatalogSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        CreateDocumentInCatalogPresenter.this.createDocumentInCatalogActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        CreateDocumentInCatalogPresenter.this.createDocumentInCatalogActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        CreateDocumentInCatalogPresenter.this.createDocumentInCatalogActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        CreateDocumentInCatalogPresenter.this.createDocumentInCatalogActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                CreateDocumentInCatalogPresenter.this.createDocumentInCatalogActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer documentId) {
            CreateDocumentInCatalogPresenter.this.createDocumentInCatalogActivity.showToastMessage("创建成功");
        }
    }
}