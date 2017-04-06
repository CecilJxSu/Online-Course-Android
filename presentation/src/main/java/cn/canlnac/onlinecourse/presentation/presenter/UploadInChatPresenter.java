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
import cn.canlnac.onlinecourse.presentation.ui.activity.PostChatActivity;

@PerActivity
public class UploadInChatPresenter implements Presenter {

    PostChatActivity uploadInChatActivity;

    private final UseCase uploadUseCase;

    private final UploadModelDataMapper uploadModelDataMapper;

    @Inject
    public UploadInChatPresenter(UseCase uploadUseCase, UploadModelDataMapper uploadModelDataMapper) {
        this.uploadUseCase = uploadUseCase;
        this.uploadModelDataMapper = uploadModelDataMapper;
    }

    public void setView(@NonNull PostChatActivity uploadInChatActivity) {
        this.uploadInChatActivity = uploadInChatActivity;
    }

    public void initialize() {
        this.uploadUseCase.execute(new UploadInChatPresenter.UploadInChatSubscriber());
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

    private final class UploadInChatSubscriber extends DefaultSubscriber<List<Upload>> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UploadInChatPresenter.this.uploadInChatActivity.showToastMessage("参数错误");
                        break;
                    case 401:
                        UploadInChatPresenter.this.uploadInChatActivity.showToastMessage("未登录");
                        UploadInChatPresenter.this.uploadInChatActivity.toLogin();
                        break;
                    default:
                        UploadInChatPresenter.this.uploadInChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                UploadInChatPresenter.this.uploadInChatActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(List<Upload> uploadList) {
            UploadInChatPresenter.this.uploadInChatActivity.postChat(uploadModelDataMapper.transform(uploadList));
        }
    }
}