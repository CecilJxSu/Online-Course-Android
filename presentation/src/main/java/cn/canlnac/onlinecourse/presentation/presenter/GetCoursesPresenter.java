package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CourseListModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetCoursesPresenter implements Presenter {

    RegisterActivity getCoursesActivity;

    private final UseCase getCoursesUseCase;

    @Inject
    public GetCoursesPresenter(UseCase getCoursesUseCase) {
        this.getCoursesUseCase = getCoursesUseCase;
    }

    public void setView(@NonNull RegisterActivity getCoursesActivity) {
        this.getCoursesActivity = getCoursesActivity;
    }

    public void initialize() {
        this.getCoursesUseCase.execute(new GetCoursesPresenter.GetCoursesSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCoursesUseCase.unsubscribe();
    }

    private final class GetCoursesSubscriber extends DefaultSubscriber<CourseListModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCoursesPresenter.this.getCoursesActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetCoursesPresenter.this.getCoursesActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetCoursesPresenter.this.getCoursesActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetCoursesPresenter.this.getCoursesActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetCoursesPresenter.this.getCoursesActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(CourseListModel courseListModel) {
            GetCoursesPresenter.this.getCoursesActivity.showToastMessage("创建成功");
        }
    }
}