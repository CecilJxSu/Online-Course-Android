package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class CreateCoursePresenter implements Presenter {

    RegisterActivity createCourseActivity;

    private final UseCase createCourseUseCase;

    @Inject
    public CreateCoursePresenter(UseCase createCourseUseCase) {
        this.createCourseUseCase = createCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity createCourseActivity) {
        this.createCourseActivity = createCourseActivity;
    }

    public void initialize() {
        this.createCourseUseCase.execute(new CreateCoursePresenter.CreateCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createCourseUseCase.unsubscribe();
    }

    private final class CreateCourseSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        CreateCoursePresenter.this.createCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        CreateCoursePresenter.this.createCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        CreateCoursePresenter.this.createCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        CreateCoursePresenter.this.createCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                CreateCoursePresenter.this.createCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer courseId) {
            CreateCoursePresenter.this.createCourseActivity.showToastMessage("创建成功");
        }
    }
}