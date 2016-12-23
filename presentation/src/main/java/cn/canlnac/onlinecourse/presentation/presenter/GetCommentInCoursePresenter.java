package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Comment;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.CommentModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInCourseFragment;

@PerActivity
public class GetCommentInCoursePresenter implements Presenter {

    PostCommentInCourseFragment getCommentInCourseActivity;

    private final UseCase getCommentInCourseUseCase;
    private final CommentModelDataMapper commentModelDataMapper;

    @Inject
    public GetCommentInCoursePresenter(UseCase getCommentInCourseUseCase,CommentModelDataMapper commentModelDataMapper) {
        this.getCommentInCourseUseCase = getCommentInCourseUseCase;
        this.commentModelDataMapper = commentModelDataMapper;
    }

    public void setView(@NonNull PostCommentInCourseFragment getCommentInCourseActivity) {
        this.getCommentInCourseActivity = getCommentInCourseActivity;
    }

    public void initialize() {
        this.getCommentInCourseUseCase.execute(new GetCommentInCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCommentInCourseUseCase.unsubscribe();
    }

    private final class GetCommentInCourseSubscriber extends DefaultSubscriber<Comment> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCommentInCoursePresenter.this.getCommentInCourseActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        GetCommentInCoursePresenter.this.getCommentInCourseActivity.showToastMessage("评论不存在");
                        break;
                    default:
                        GetCommentInCoursePresenter.this.getCommentInCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetCommentInCoursePresenter.this.getCommentInCourseActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Comment comment) {
            GetCommentInCoursePresenter.this.getCommentInCourseActivity.getComment(commentModelDataMapper.transform(comment));
        }
    }
}
