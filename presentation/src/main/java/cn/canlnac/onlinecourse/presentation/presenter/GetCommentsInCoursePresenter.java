package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CommentListModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetCommentsInCoursePresenter implements Presenter {

    RegisterActivity getCommentsInCourseActivity;

    private final UseCase getCommentsInCourseUseCase;

    @Inject
    public GetCommentsInCoursePresenter(UseCase getCommentsInCourseUseCase) {
        this.getCommentsInCourseUseCase = getCommentsInCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity getCommentsInCourseActivity) {
        this.getCommentsInCourseActivity = getCommentsInCourseActivity;
    }

    public void initialize() {
        this.getCommentsInCourseUseCase.execute(new GetCommentsInCoursePresenter.GetCommentsInCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCommentsInCourseUseCase.unsubscribe();
    }

    private final class GetCommentsInCourseSubscriber extends DefaultSubscriber<CommentListModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCommentsInCoursePresenter.this.getCommentsInCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetCommentsInCoursePresenter.this.getCommentsInCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetCommentsInCoursePresenter.this.getCommentsInCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetCommentsInCoursePresenter.this.getCommentsInCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetCommentsInCoursePresenter.this.getCommentsInCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(CommentListModel commentListModel) {
            GetCommentsInCoursePresenter.this.getCommentsInCourseActivity.showToastMessage("创建成功");
        }
    }
}