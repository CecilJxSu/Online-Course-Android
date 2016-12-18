package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UpdateCoursePresenter implements Presenter {

    RegisterActivity updateCourseActivity;

    private final UseCase updateCourseUseCase;

    @Inject
    public UpdateCoursePresenter(UseCase updateCourseUseCase) {
        this.updateCourseUseCase = updateCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity updateCourseActivity) {
        this.updateCourseActivity = updateCourseActivity;
    }

    public void initialize() {
        this.updateCourseUseCase.execute(new UpdateCoursePresenter.UpdateCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.updateCourseUseCase.unsubscribe();
    }

    private final class UpdateCourseSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UpdateCoursePresenter.this.updateCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UpdateCoursePresenter.this.updateCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UpdateCoursePresenter.this.updateCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UpdateCoursePresenter.this.updateCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UpdateCoursePresenter.this.updateCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UpdateCoursePresenter.this.updateCourseActivity.showToastMessage("创建成功");
        }
    }
}