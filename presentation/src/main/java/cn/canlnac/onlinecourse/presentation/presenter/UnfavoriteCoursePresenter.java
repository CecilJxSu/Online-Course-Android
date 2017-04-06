package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;

@PerActivity
public class UnfavoriteCoursePresenter implements Presenter {

    CourseActivity unfavoriteCourseActivity;

    private final UseCase unfavoriteCourseUseCase;

    @Inject
    public UnfavoriteCoursePresenter(UseCase unfavoriteCourseUseCase) {
        this.unfavoriteCourseUseCase = unfavoriteCourseUseCase;
    }

    public void setView(@NonNull CourseActivity unfavoriteCourseActivity) {
        this.unfavoriteCourseActivity = unfavoriteCourseActivity;
    }

    public void initialize() {
        this.unfavoriteCourseUseCase.execute(new UnfavoriteCoursePresenter.UnfavoriteCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.unfavoriteCourseUseCase.unsubscribe();
    }

    private final class UnfavoriteCourseSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                    case 404:
                        UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("课程不存在");
                        break;
                    case 401:
                        UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("未登陆");
                        UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.toLogin();
                        break;
                    default:
                        UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.toggleFavorite(false);
        }
    }
}