package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class LikeCoursePresenter implements Presenter {

    RegisterActivity likeCourseActivity;

    private final UseCase likeCourseUseCase;

    @Inject
    public LikeCoursePresenter(UseCase likeCourseUseCase) {
        this.likeCourseUseCase = likeCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity likeCourseActivity) {
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
                    case 400:
                        LikeCoursePresenter.this.likeCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        LikeCoursePresenter.this.likeCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        LikeCoursePresenter.this.likeCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        LikeCoursePresenter.this.likeCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                LikeCoursePresenter.this.likeCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            LikeCoursePresenter.this.likeCourseActivity.showToastMessage("创建成功");
        }
    }
}