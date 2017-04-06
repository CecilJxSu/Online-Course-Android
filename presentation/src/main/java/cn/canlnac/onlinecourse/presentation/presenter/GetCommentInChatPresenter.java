package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Comment;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.CommentModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInChatFragment;

@PerActivity
public class GetCommentInChatPresenter implements Presenter {

    PostCommentInChatFragment getCommentInChatActivity;

    private final UseCase getCommentInChatUseCase;
    private final CommentModelDataMapper commentModelDataMapper;

    @Inject
    public GetCommentInChatPresenter(UseCase getCommentInChatUseCase, CommentModelDataMapper commentModelDataMapper) {
        this.getCommentInChatUseCase = getCommentInChatUseCase;
        this.commentModelDataMapper = commentModelDataMapper;
    }

    public void setView(@NonNull PostCommentInChatFragment getCommentInChatActivity) {
        this.getCommentInChatActivity = getCommentInChatActivity;
    }

    public void initialize() {
        this.getCommentInChatUseCase.execute(new GetCommentInChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCommentInChatUseCase.unsubscribe();
    }

    private final class GetCommentInChatSubscriber extends DefaultSubscriber<Comment> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCommentInChatPresenter.this.getCommentInChatActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        GetCommentInChatPresenter.this.getCommentInChatActivity.showToastMessage("评论不存在");
                        break;
                    default:
                        GetCommentInChatPresenter.this.getCommentInChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetCommentInChatPresenter.this.getCommentInChatActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Comment comment) {
            GetCommentInChatPresenter.this.getCommentInChatActivity.getComment(commentModelDataMapper.transform(comment));
        }
    }
}
