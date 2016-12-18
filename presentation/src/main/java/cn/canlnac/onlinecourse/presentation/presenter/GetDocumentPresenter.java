package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.DocumentModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetDocumentPresenter implements Presenter {

    RegisterActivity getDocumentActivity;

    private final UseCase getDocumentUseCase;

    @Inject
    public GetDocumentPresenter(UseCase getDocumentUseCase) {
        this.getDocumentUseCase = getDocumentUseCase;
    }

    public void setView(@NonNull RegisterActivity getDocumentActivity) {
        this.getDocumentActivity = getDocumentActivity;
    }

    public void initialize() {
        this.getDocumentUseCase.execute(new GetDocumentPresenter.GetDocumentSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getDocumentUseCase.unsubscribe();
    }

    private final class GetDocumentSubscriber extends DefaultSubscriber<DocumentModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetDocumentPresenter.this.getDocumentActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetDocumentPresenter.this.getDocumentActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetDocumentPresenter.this.getDocumentActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetDocumentPresenter.this.getDocumentActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetDocumentPresenter.this.getDocumentActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(DocumentModel documentModel) {
            GetDocumentPresenter.this.getDocumentActivity.showToastMessage("创建成功");
        }
    }
}