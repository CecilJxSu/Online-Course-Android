package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.CommentList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.CommentListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseCommentFragment;

@PerActivity
public class GetCommentsInCoursePresenter implements Presenter {

    CourseCommentFragment getCommentsInCourseFragment;

    private final UseCase getCommentsInCourseUseCase;
    private final CommentListModelDataMapper commentListModelDataMapper;

    private int state;

    @Inject
    public GetCommentsInCoursePresenter(UseCase getCommentsInCourseUseCase,CommentListModelDataMapper commentListModelDataMapper) {
        this.getCommentsInCourseUseCase = getCommentsInCourseUseCase;
        this.commentListModelDataMapper = commentListModelDataMapper;
    }

    /**
     * 设置View
     * @param getCommentsInCourseFragment   view
     * @param state                         0，刷新；1，加载更多
     */
    public void setView(@NonNull CourseCommentFragment getCommentsInCourseFragment, int state) {
        this.getCommentsInCourseFragment = getCommentsInCourseFragment;
        this.state = state;
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

    private final class GetCommentsInCourseSubscriber extends DefaultSubscriber<CommentList> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        if (state == 0) {
                            GetCommentsInCoursePresenter.this.getCommentsInCourseFragment.showRefreshError("参数错误");
                        }
                        break;
                    case 404:
                        if (state == 0) {
                            GetCommentsInCoursePresenter.this.getCommentsInCourseFragment.showRefreshError("没有评论");
                        }
                        break;
                    default:
                        if (state == 0) {
                            GetCommentsInCoursePresenter.this.getCommentsInCourseFragment.showRefreshError("加载失败");
                        }
                        GetCommentsInCoursePresenter.this.getCommentsInCourseFragment.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                if (state == 0) {
                    GetCommentsInCoursePresenter.this.getCommentsInCourseFragment.showRefreshError("加载失败");
                }
                GetCommentsInCoursePresenter.this.getCommentsInCourseFragment.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(CommentList commentList) {
            if (state == 0) {
                GetCommentsInCoursePresenter.this.getCommentsInCourseFragment.showRefreshComments(commentListModelDataMapper.transform(commentList));
            } else if (state == 1) {
                GetCommentsInCoursePresenter.this.getCommentsInCourseFragment.showLoadMoreComments(commentListModelDataMapper.transform(commentList));
            }
        }
    }
}