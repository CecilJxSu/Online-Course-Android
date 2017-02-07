package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Upload;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.UploadModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.CutActivity;

@PerActivity
public class UploadInCutPresenter implements Presenter {

    CutActivity uploadInCutActivity;

    private final UseCase uploadUseCase;

    private final UploadModelDataMapper uploadModelDataMapper;

    @Inject
    public UploadInCutPresenter(UseCase uploadUseCase, UploadModelDataMapper uploadModelDataMapper) {
        this.uploadUseCase = uploadUseCase;
        this.uploadModelDataMapper = uploadModelDataMapper;
    }

    public void setView(@NonNull CutActivity uploadInCutActivity) {
        this.uploadInCutActivity = uploadInCutActivity;
    }

    public void initialize() {
        this.uploadUseCase.execute(new UploadInCutPresenter.UploadInCutSubscriber());
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

    private final class UploadInCutSubscriber extends DefaultSubscriber<List<Upload>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UploadInCutPresenter.this.uploadInCutActivity.showToastMessage("参数错误");
                        break;
                    case 401:
                        UploadInCutPresenter.this.uploadInCutActivity.showToastMessage("未登录");
                        UploadInCutPresenter.this.uploadInCutActivity.toLogin();
                        break;
                    default:
                        UploadInCutPresenter.this.uploadInCutActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                UploadInCutPresenter.this.uploadInCutActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(List<Upload> uploadList) {
            UploadInCutPresenter.this.uploadInCutActivity.uploadReturn(uploadModelDataMapper.transform(uploadList));
        }
    }
}