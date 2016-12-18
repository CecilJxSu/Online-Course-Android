package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class FavoriteCoursePresenter implements Presenter {

    RegisterActivity favoriteCourseActivity;

    private final UseCase favoriteCourseUseCase;

    @Inject
    public FavoriteCoursePresenter(UseCase favoriteCourseUseCase) {
        this.favoriteCourseUseCase = favoriteCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity favoriteCourseActivity) {
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
                    case 400:
                        FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            FavoriteCoursePresenter.this.favoriteCourseActivity.showToastMessage("创建成功");
        }
    }
}