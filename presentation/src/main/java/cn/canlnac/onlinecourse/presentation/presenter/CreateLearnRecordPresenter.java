package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class CreateLearnRecordPresenter implements Presenter {

    RegisterActivity createLearnRecordActivity;

    private final UseCase createLearnRecordUseCase;

    @Inject
    public CreateLearnRecordPresenter(UseCase createLearnRecordUseCase) {
        this.createLearnRecordUseCase = createLearnRecordUseCase;
    }

    public void setView(@NonNull RegisterActivity createLearnRecordActivity) {
        this.createLearnRecordActivity = createLearnRecordActivity;
    }

    public void initialize() {
        this.createLearnRecordUseCase.execute(new CreateLearnRecordPresenter.CreateLearnRecordSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createLearnRecordUseCase.unsubscribe();
    }

    private final class CreateLearnRecordSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        CreateLearnRecordPresenter.this.createLearnRecordActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        CreateLearnRecordPresenter.this.createLearnRecordActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        CreateLearnRecordPresenter.this.createLearnRecordActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        CreateLearnRecordPresenter.this.createLearnRecordActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                CreateLearnRecordPresenter.this.createLearnRecordActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer learnRecordId) {
            CreateLearnRecordPresenter.this.createLearnRecordActivity.showToastMessage("创建成功");
        }
    }
}