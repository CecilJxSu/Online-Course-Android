package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.LearnRecordListModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetOtherUserLearnRecordPresenter implements Presenter {

    RegisterActivity getOtherUserLearnRecordActivity;

    private final UseCase getOtherUserLearnRecordUseCase;

    @Inject
    public GetOtherUserLearnRecordPresenter(UseCase getOtherUserLearnRecordUseCase) {
        this.getOtherUserLearnRecordUseCase = getOtherUserLearnRecordUseCase;
    }

    public void setView(@NonNull RegisterActivity getOtherUserLearnRecordActivity) {
        this.getOtherUserLearnRecordActivity = getOtherUserLearnRecordActivity;
    }

    public void initialize() {
        this.getOtherUserLearnRecordUseCase.execute(new GetOtherUserLearnRecordPresenter.GetOtherUserLearnRecordSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getOtherUserLearnRecordUseCase.unsubscribe();
    }

    private final class GetOtherUserLearnRecordSubscriber extends DefaultSubscriber<LearnRecordListModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetOtherUserLearnRecordPresenter.this.getOtherUserLearnRecordActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetOtherUserLearnRecordPresenter.this.getOtherUserLearnRecordActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetOtherUserLearnRecordPresenter.this.getOtherUserLearnRecordActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetOtherUserLearnRecordPresenter.this.getOtherUserLearnRecordActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetOtherUserLearnRecordPresenter.this.getOtherUserLearnRecordActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(LearnRecordListModel learnRecordListModel) {
            GetOtherUserLearnRecordPresenter.this.getOtherUserLearnRecordActivity.showToastMessage("创建成功");
        }
    }
}