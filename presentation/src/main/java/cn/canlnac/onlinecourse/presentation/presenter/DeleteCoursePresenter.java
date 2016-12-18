package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class DeleteCoursePresenter implements Presenter {

    RegisterActivity deleteCourseActivity;

    private final UseCase deleteCourseUseCase;

    @Inject
    public DeleteCoursePresenter(UseCase deleteCourseUseCase) {
        this.deleteCourseUseCase = deleteCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity deleteCourseActivity) {
        this.deleteCourseActivity = deleteCourseActivity;
    }

    public void initialize() {
        this.deleteCourseUseCase.execute(new DeleteCoursePresenter.DeleteCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.deleteCourseUseCase.unsubscribe();
    }

    private final class DeleteCourseSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        DeleteCoursePresenter.this.deleteCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        DeleteCoursePresenter.this.deleteCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        DeleteCoursePresenter.this.deleteCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        DeleteCoursePresenter.this.deleteCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                DeleteCoursePresenter.this.deleteCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            DeleteCoursePresenter.this.deleteCourseActivity.showToastMessage("创建成功");
        }
    }
}