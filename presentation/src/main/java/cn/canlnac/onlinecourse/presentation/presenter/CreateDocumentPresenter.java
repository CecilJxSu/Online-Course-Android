package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class CreateDocumentPresenter implements Presenter {

    RegisterActivity createDocumentActivity;

    private final UseCase createDocumentUseCase;

    @Inject
    public CreateDocumentPresenter(UseCase createDocumentUseCase) {
        this.createDocumentUseCase = createDocumentUseCase;
    }

    public void setView(@NonNull RegisterActivity createDocumentActivity) {
        this.createDocumentActivity = createDocumentActivity;
    }

    public void initialize() {
        this.createDocumentUseCase.execute(new CreateDocumentPresenter.CreateDocumentSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createDocumentUseCase.unsubscribe();
    }

    private final class CreateDocumentSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        CreateDocumentPresenter.this.createDocumentActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        CreateDocumentPresenter.this.createDocumentActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        CreateDocumentPresenter.this.createDocumentActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        CreateDocumentPresenter.this.createDocumentActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                CreateDocumentPresenter.this.createDocumentActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer documentId) {
            CreateDocumentPresenter.this.createDocumentActivity.showToastMessage("创建成功");
        }
    }
}