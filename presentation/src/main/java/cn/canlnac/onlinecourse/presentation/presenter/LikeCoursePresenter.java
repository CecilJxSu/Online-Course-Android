package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;
import android.system.ErrnoException;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;

@PerActivity
public class LikeCoursePresenter implements Presenter {

    CourseActivity likeCourseActivity;

    private final UseCase likeCourseUseCase;

    @Inject
    public LikeCoursePresenter(UseCase likeCourseUseCase) {
        this.likeCourseUseCase = likeCourseUseCase;
    }

    public void setView(@NonNull CourseActivity likeCourseActivity) {
        this.likeCourseActivity = likeCourseActivity;
    }

    public void initialize() {
        this.likeCourseUseCase.execute(new LikeCoursePresenter.LikeCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.likeCourseUseCase.unsubscribe();
    }

    private final class LikeCourseSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 304:
                        LikeCoursePresenter.this.likeCourseActivity.showToastMessage("不能重复点赞");
                        break;
                    case 400:
                    case 404:
                        LikeCoursePresenter.this.likeCourseActivity.showToastMessage("课程不存在");
                        break;
                    case 401:
                        LikeCoursePresenter.this.likeCourseActivity.showToastMessage("未登陆");
                        LikeCoursePresenter.this.likeCourseActivity.toLogin();
                        break;
                    default:
                        LikeCoursePresenter.this.likeCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                LikeCoursePresenter.this.likeCourseActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Void empty) {
            LikeCoursePresenter.this.likeCourseActivity.toggleLike(true);
        }
    }
}