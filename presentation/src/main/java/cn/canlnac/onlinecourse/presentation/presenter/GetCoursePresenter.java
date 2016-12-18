package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetCoursePresenter implements Presenter {

    RegisterActivity getCourseActivity;

    private final UseCase getCourseUseCase;

    @Inject
    public GetCoursePresenter(UseCase getCourseUseCase) {
        this.getCourseUseCase = getCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity getCourseActivity) {
        this.getCourseActivity = getCourseActivity;
    }

    public void initialize() {
        this.getCourseUseCase.execute(new GetCoursePresenter.GetCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCourseUseCase.unsubscribe();
    }

    private final class GetCourseSubscriber extends DefaultSubscriber<CourseModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCoursePresenter.this.getCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetCoursePresenter.this.getCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetCoursePresenter.this.getCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetCoursePresenter.this.getCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetCoursePresenter.this.getCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(CourseModel courseModel) {
            GetCoursePresenter.this.getCourseActivity.showToastMessage("创建成功");
        }
    }
}