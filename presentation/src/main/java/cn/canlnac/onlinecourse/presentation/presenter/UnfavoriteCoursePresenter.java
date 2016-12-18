package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UnfavoriteCoursePresenter implements Presenter {

    RegisterActivity unfavoriteCourseActivity;

    private final UseCase unfavoriteCourseUseCase;

    @Inject
    public UnfavoriteCoursePresenter(UseCase unfavoriteCourseUseCase) {
        this.unfavoriteCourseUseCase = unfavoriteCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity unfavoriteCourseActivity) {
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
                        UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnfavoriteCoursePresenter.this.unfavoriteCourseActivity.showToastMessage("创建成功");
        }
    }
}