package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UpdateLearnRecordPresenter implements Presenter {

    RegisterActivity updateLearnRecordActivity;

    private final UseCase updateLearnRecordUseCase;

    @Inject
    public UpdateLearnRecordPresenter(UseCase updateLearnRecordUseCase) {
        this.updateLearnRecordUseCase = updateLearnRecordUseCase;
    }

    public void setView(@NonNull RegisterActivity updateLearnRecordActivity) {
        this.updateLearnRecordActivity = updateLearnRecordActivity;
    }

    public void initialize() {
        this.updateLearnRecordUseCase.execute(new UpdateLearnRecordPresenter.UpdateLearnRecordSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.updateLearnRecordUseCase.unsubscribe();
    }

    private final class UpdateLearnRecordSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UpdateLearnRecordPresenter.this.updateLearnRecordActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UpdateLearnRecordPresenter.this.updateLearnRecordActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UpdateLearnRecordPresenter.this.updateLearnRecordActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UpdateLearnRecordPresenter.this.updateLearnRecordActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UpdateLearnRecordPresenter.this.updateLearnRecordActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UpdateLearnRecordPresenter.this.updateLearnRecordActivity.showToastMessage("创建成功");
        }
    }
}