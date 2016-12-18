package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.FollowerModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetUserFollowingPresenter implements Presenter {

    RegisterActivity getUserFollowingActivity;

    private final UseCase getUserFollowingUseCase;

    @Inject
    public GetUserFollowingPresenter(UseCase getUserFollowingUseCase) {
        this.getUserFollowingUseCase = getUserFollowingUseCase;
    }

    public void setView(@NonNull RegisterActivity getUserFollowingActivity) {
        this.getUserFollowingActivity = getUserFollowingActivity;
    }

    public void initialize() {
        this.getUserFollowingUseCase.execute(new GetUserFollowingPresenter.GetUserFollowingSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getUserFollowingUseCase.unsubscribe();
    }

    private final class GetUserFollowingSubscriber extends DefaultSubscriber<FollowerModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetUserFollowingPresenter.this.getUserFollowingActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetUserFollowingPresenter.this.getUserFollowingActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetUserFollowingPresenter.this.getUserFollowingActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetUserFollowingPresenter.this.getUserFollowingActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetUserFollowingPresenter.this.getUserFollowingActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(FollowerModel followerModel) {
            GetUserFollowingPresenter.this.getUserFollowingActivity.showToastMessage("创建成功");
        }
    }
}