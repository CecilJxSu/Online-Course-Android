package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.DocumentListModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetDocumentsInCoursePresenter implements Presenter {

    RegisterActivity getDocumentsInCourseActivity;

    private final UseCase getDocumentsInCourseUseCase;

    @Inject
    public GetDocumentsInCoursePresenter(UseCase getDocumentsInCourseUseCase) {
        this.getDocumentsInCourseUseCase = getDocumentsInCourseUseCase;
    }

    public void setView(@NonNull RegisterActivity getDocumentsInCourseActivity) {
        this.getDocumentsInCourseActivity = getDocumentsInCourseActivity;
    }

    public void initialize() {
        this.getDocumentsInCourseUseCase.execute(new GetDocumentsInCoursePresenter.GetDocumentsInCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getDocumentsInCourseUseCase.unsubscribe();
    }

    private final class GetDocumentsInCourseSubscriber extends DefaultSubscriber<DocumentListModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(DocumentListModel documentListModel) {
            GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showToastMessage("创建成功");
        }
    }
}