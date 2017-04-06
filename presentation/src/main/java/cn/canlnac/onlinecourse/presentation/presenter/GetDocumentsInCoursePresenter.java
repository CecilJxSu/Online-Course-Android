package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.DocumentList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.DocumentListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.DocumentFragment;

@PerActivity
public class GetDocumentsInCoursePresenter implements Presenter {

    DocumentFragment getDocumentsInCourseActivity;

    private final UseCase getDocumentsInCourseUseCase;
    private final DocumentListModelDataMapper documentListModelDataMapper;

    private int state;

    @Inject
    public GetDocumentsInCoursePresenter(UseCase getDocumentsInCourseUseCase, DocumentListModelDataMapper documentListModelDataMapper) {
        this.getDocumentsInCourseUseCase = getDocumentsInCourseUseCase;
        this.documentListModelDataMapper = documentListModelDataMapper;
    }

    public void setView(@NonNull DocumentFragment getDocumentsInCourseActivity, int state) {
        this.getDocumentsInCourseActivity = getDocumentsInCourseActivity;
        this.state = state;
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

    private final class GetDocumentsInCourseSubscriber extends DefaultSubscriber<DocumentList> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        if (state == 0) {
                            GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showRefreshError("参数错误");
                        }
                        break;
                    case 404:
                        if (state == 0) {
                            GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showRefreshError("没有文档");
                        }
                        break;
                    default:
                        if (state == 0) {
                            GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showRefreshError("加载失败");
                        }
                        GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(DocumentList documentList) {
            if (state == 0) {
                GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showRefresh(documentListModelDataMapper.transform(documentList));
            } else if (state == 1) {
                GetDocumentsInCoursePresenter.this.getDocumentsInCourseActivity.showLoadMore(documentListModelDataMapper.transform(documentList));
            }
        }
    }
}