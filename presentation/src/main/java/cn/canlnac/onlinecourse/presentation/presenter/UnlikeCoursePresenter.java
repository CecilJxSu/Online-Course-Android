package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UnlikeCoursePresenter implements Presenter {

    RegisterActivity unlikeCourseActivity;

    private final UseCase unlikeCourseUseCase;

    @Inject
    public UnlikeCoursePresenter(UseCase unlikeCourseUseCase) {
        this.unlikeCourseUseCase = unlikeCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity unlikeCourseActivity) {
        this.unlikeCourseActivity = unlikeCourseActivity;
    }

    public void initialize() {
        this.unlikeCourseUseCase.execute(new UnlikeCoursePresenter.UnlikeCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.unlikeCourseUseCase.unsubscribe();
    }

    private final class UnlikeCourseSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UnlikeCoursePresenter.this.unlikeCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UnlikeCoursePresenter.this.unlikeCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UnlikeCoursePresenter.this.unlikeCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UnlikeCoursePresenter.this.unlikeCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UnlikeCoursePresenter.this.unlikeCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnlikeCoursePresenter.this.unlikeCourseActivity.showToastMessage("创建成功");
        }
    }
}