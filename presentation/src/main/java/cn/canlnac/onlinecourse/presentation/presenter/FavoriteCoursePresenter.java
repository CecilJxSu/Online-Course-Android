package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;

@PerActivity
public class FavoriteCoursePresenter implements Presenter {

    CourseActivity favoriteCourseActivity;

    private final UseCase favoriteCourseUseCase;

    @Inject
    public FavoriteCoursePresenter(UseCase favoriteCourseUseCase) {
        this.favoriteCourseUseCase = favoriteCourseUseCase;
    }

    public void setView(@NonNull CourseActivity favoriteCourseActivity) {
        this.favoriteCourseActivity = favoriteCourseActivity;
    }

    public void initialize() {
        this.favoriteCourseUseCase.execute(new FavoriteCoursePresenter.FavoriteCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.favoriteCourseUseCase.unsubscribe();
    }

    private final class FavoriteCourseSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 304:
                        FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("不能重复收藏");
                        break;
                    case 400:
                    case 404:
                        FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("课程不存在");
                        break;
                    case 401:
                        FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("未登陆");
                        FavoriteCoursePresenter.this.favoriteCourseActivity.toLogin();
                        break;
                    default:
                        FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Void empty) {
            FavoriteCoursePresenter.this.favoriteCourseActivity.toggleFavorite(true);
        }
    }
}