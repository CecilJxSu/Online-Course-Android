package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Profile;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.ProfileModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.ChatActivity;

@PerActivity
public class GetUserProfileInChatPresenter implements Presenter {

    ChatActivity chatActivity;

    private final UseCase getUserProfileUseCase;
    private final ProfileModelDataMapper profileModelDataMapper;

    @Inject
    public GetUserProfileInChatPresenter(UseCase getUserProfileUseCase, ProfileModelDataMapper profileModelDataMapper) {
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.profileModelDataMapper = profileModelDataMapper;
    }

    public void setView(@NonNull ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
    }

    public void initialize() {
        this.getUserProfileUseCase.execute(new GetUserProfileInChatPresenter.GetUserProfileSubscriber());
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
                        GetUserProfileInChatPresenter.this.chatActivity.showToastMessage("作者不存在");
                        break;
                    case 401:
                        GetUserProfileInChatPresenter.this.chatActivity.showToastMessage("未登陆");
                        GetUserProfileInChatPresenter.this.chatActivity.toLogin();
                        break;
                    default:
                        GetUserProfileInChatPresenter.this.chatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetUserProfileInChatPresenter.this.chatActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Profile profile) {
            GetUserProfileInChatPresenter.this.chatActivity.showProfile(profileModelDataMapper.transform(profile));
        }
    }
}