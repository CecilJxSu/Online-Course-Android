package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Profile;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.ProfileModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseIntroFragment;

/**
 * 课程简介.
 */
@PerActivity
public class CourseIntroPresenter implements Presenter {

    CourseIntroFragment courseIntroFragment;

    private final UseCase getUserProfileUseCase;

    private final ProfileModelDataMapper profileModelDataMapper;

    @Inject
    public CourseIntroPresenter(
            UseCase getUserProfileUseCase,
            ProfileModelDataMapper profileModelDataMapper
    ) {
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.profileModelDataMapper = profileModelDataMapper;
    }

    public void setView(@NonNull CourseIntroFragment courseIntroFragment) {
        this.courseIntroFragment = courseIntroFragment;
    }

    public void initializeProfile() {
        this.getUserProfileUseCase.execute(new CourseIntroPresenter.CourseIntroSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    private final class CourseIntroSubscriber extends DefaultSubscriber<Profile> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 404:
                        CourseIntroPresenter.this.courseIntroFragment.showToastMessage("作者不存在");
                        CourseIntroPresenter.this.courseIntroFragment.getActivity().finish();
                        break;
                    case 401:
                        CourseIntroPresenter.this.courseIntroFragment.showToastMessage("未登陆");
                        CourseIntroPresenter.this.courseIntroFragment.toLogin();
                        break;
                    default:
                        CourseIntroPresenter.this.courseIntroFragment.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                CourseIntroPresenter.this.courseIntroFragment.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Profile profile) {
            if (profile != null) {
                CourseIntroPresenter.this.courseIntroFragment.showAuthor(profileModelDataMapper.transform(profile));
            }
        }
    }
}
