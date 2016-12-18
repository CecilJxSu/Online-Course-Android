package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.ProfileModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetUserProfilePresenter implements Presenter {

    RegisterActivity getUserProfileActivity;

    private final UseCase getUserProfileUseCase;

    @Inject
    public GetUserProfilePresenter(UseCase getUserProfileUseCase) {
        this.getUserProfileUseCase = getUserProfileUseCase;
    }

    public void setView(@NonNull RegisterActivity getUserProfileActivity) {
        this.getUserProfileActivity = getUserProfileActivity;
    }

    public void initialize() {
        this.getUserProfileUseCase.execute(new GetUserProfilePresenter.GetUserProfileSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getUserProfileUseCase.unsubscribe();
    }

    private final class GetUserProfileSubscriber extends DefaultSubscriber<ProfileModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetUserProfilePresenter.this.getUserProfileActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetUserProfilePresenter.this.getUserProfileActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetUserProfilePresenter.this.getUserProfileActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetUserProfilePresenter.this.getUserProfileActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetUserProfilePresenter.this.getUserProfileActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(ProfileModel profileModel) {
            GetUserProfilePresenter.this.getUserProfileActivity.showToastMessage("创建成功");
        }
    }
}