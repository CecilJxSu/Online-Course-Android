package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInCourseFragment;

@PerActivity
public class CreateCommentInCoursePresenter implements Presenter {

    PostCommentInCourseFragment createCommentInCourseActivity;

    private final UseCase createCommentInCourseUseCase;

    @Inject
    public CreateCommentInCoursePresenter(UseCase createCommentInCourseUseCase) {
        this.createCommentInCourseUseCase = createCommentInCourseUseCase;
    }

    public void setView(@NonNull PostCommentInCourseFragment createCommentInCourseActivity) {
        this.createCommentInCourseActivity = createCommentInCourseActivity;
    }

    public void initialize() {
        this.createCommentInCourseUseCase.execute(new CreateCommentInCoursePresenter.CreateCommentInCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createCommentInCourseUseCase.unsubscribe();
    }

    private final class CreateCommentInCourseSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        CreateCommentInCoursePresenter.this.createCommentInCourseActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        CreateCommentInCoursePresenter.this.createCommentInCourseActivity.showToastMessage("课程不存在");
                        break;
                    case 401:
                        CreateCommentInCoursePresenter.this.createCommentInCourseActivity.showToastMessage("未登陆");
                        CreateCommentInCoursePresenter.this.createCommentInCourseActivity.toLogin();
                        break;
                    default:
                        CreateCommentInCoursePresenter.this.createCommentInCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                CreateCommentInCoursePresenter.this.createCommentInCourseActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Integer commentId) {
            CreateCommentInCoursePresenter.this.createCommentInCourseActivity.showToastMessage("创建成功");
            CreateCommentInCoursePresenter.this.createCommentInCourseActivity.postSuccess(commentId);
        }
    }
}
