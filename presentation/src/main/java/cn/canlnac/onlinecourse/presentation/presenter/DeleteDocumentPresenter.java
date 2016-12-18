package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class DeleteDocumentPresenter implements Presenter {

    RegisterActivity deleteDocumentActivity;

    private final UseCase deleteDocumentUseCase;

    @Inject
    public DeleteDocumentPresenter(UseCase deleteDocumentUseCase) {
        this.deleteDocumentUseCase = deleteDocumentUseCase;
    }

    public void setView(@NonNull RegisterActivity deleteDocumentActivity) {
        this.deleteDocumentActivity = deleteDocumentActivity;
    }

    public void initialize() {
        this.deleteDocumentUseCase.execute(new DeleteDocumentPresenter.DeleteDocumentSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.deleteDocumentUseCase.unsubscribe();
    }

    private final class DeleteDocumentSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        DeleteDocumentPresenter.this.deleteDocumentActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        DeleteDocumentPresenter.this.deleteDocumentActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        DeleteDocumentPresenter.this.deleteDocumentActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        DeleteDocumentPresenter.this.deleteDocumentActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                DeleteDocumentPresenter.this.deleteDocumentActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            DeleteDocumentPresenter.this.deleteDocumentActivity.showToastMessage("创建成功");
        }
    }
}