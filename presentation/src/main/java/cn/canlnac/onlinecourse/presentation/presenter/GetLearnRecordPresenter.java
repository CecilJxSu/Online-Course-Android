package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.LearnRecordModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetLearnRecordPresenter implements Presenter {

    RegisterActivity getLearnRecordActivity;

    private final UseCase getLearnRecordUseCase;

    @Inject
    public GetLearnRecordPresenter(UseCase getLearnRecordUseCase) {
        this.getLearnRecordUseCase = getLearnRecordUseCase;
    }

    public void setView(@NonNull RegisterActivity getLearnRecordActivity) {
        this.getLearnRecordActivity = getLearnRecordActivity;
    }

    public void initialize() {
        this.getLearnRecordUseCase.execute(new GetLearnRecordPresenter.GetLearnRecordSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getLearnRecordUseCase.unsubscribe();
    }

    private final class GetLearnRecordSubscriber extends DefaultSubscriber<LearnRecordModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetLearnRecordPresenter.this.getLearnRecordActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetLearnRecordPresenter.this.getLearnRecordActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetLearnRecordPresenter.this.getLearnRecordActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetLearnRecordPresenter.this.getLearnRecordActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetLearnRecordPresenter.this.getLearnRecordActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(LearnRecordModel learnRecordModel) {
            GetLearnRecordPresenter.this.getLearnRecordActivity.showToastMessage("创建成功");
        }
    }
}