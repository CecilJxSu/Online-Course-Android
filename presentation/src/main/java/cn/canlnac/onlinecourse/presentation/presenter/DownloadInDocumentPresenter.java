package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import java.io.File;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.fragment.DocumentFragment;

@PerActivity
public class DownloadInDocumentPresenter implements Presenter {

    DocumentFragment downloadInDocumentActivity;

    private final UseCase uploadUseCase;

    private String type;

    @Inject
    public DownloadInDocumentPresenter(UseCase uploadUseCase) {
        this.uploadUseCase = uploadUseCase;
    }

    public void setView(@NonNull DocumentFragment downloadInDocumentActivity, String type) {
        this.downloadInDocumentActivity = downloadInDocumentActivity;
        this.type = type;
    }

    public void initialize() {
        this.uploadUseCase.execute(new DownloadInDocumentPresenter.DownloadInDocumentSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.uploadUseCase.unsubscribe();
    }

    private final class DownloadInDocumentSubscriber extends DefaultSubscriber<File> {
        @Override
        public void onCompleted() {
            DownloadInDocumentPresenter.this.downloadInDocumentActivity.setShowingFile(false);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            DownloadInDocumentPresenter.this.downloadInDocumentActivity.showToastMessage("网络连接错误");
            DownloadInDocumentPresenter.this.downloadInDocumentActivity.setShowingFile(false);
        }

        @Override
        public void onNext(File file) {
            DownloadInDocumentPresenter.this.downloadInDocumentActivity.setShowingFile(false);
            DownloadInDocumentPresenter.this.downloadInDocumentActivity.showFile(type,file);
        }
    }
}