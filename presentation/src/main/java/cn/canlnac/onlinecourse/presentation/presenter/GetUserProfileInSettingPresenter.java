package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Profile;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.ProfileModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.ProfileActivity;

@PerActivity
public class GetUserProfileInSettingPresenter implements Presenter {

    ProfileActivity profileActivity;

    private final UseCase getUserProfileUseCase;
    private final ProfileModelDataMapper profileModelDataMapper;

    @Inject
    public GetUserProfileInSettingPresenter(UseCase getUserProfileUseCase, ProfileModelDataMapper profileModelDataMapper) {
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.profileModelDataMapper = profileModelDataMapper;
    }

    public void setView(@NonNull ProfileActivity profileActivity) {
        this.profileActivity = profileActivity;
    }

    public void initialize() {
        this.getUserProfileUseCase.execute(new GetUserProfileInSettingPresenter.GetUserProfileSubscriber());
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

    private final class GetUserProfileSubscriber extends DefaultSubscriber<Profile> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 404:
                        GetUserProfileInSettingPresenter.this.profileActivity.showToastMessage("用户存在");
                        break;
                    case 401:
                        GetUserProfileInSettingPresenter.this.profileActivity.showToastMessage("未登陆");
                        GetUserProfileInSettingPresenter.this.profileActivity.toLogin();
                        break;
                    default:
                        GetUserProfileInSettingPresenter.this.profileActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetUserProfileInSettingPresenter.this.profileActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Profile profile) {
            GetUserProfileInSettingPresenter.this.profileActivity.showProfile(profileModelDataMapper.transform(profile));
        }
    }
}