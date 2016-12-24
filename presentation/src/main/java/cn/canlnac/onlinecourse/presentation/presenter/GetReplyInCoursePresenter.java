package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Reply;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.ReplyModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostReplyInCourseFragment;

@PerActivity
public class GetReplyInCoursePresenter implements Presenter {

    PostReplyInCourseFragment replyCommentInCourseActivity;

    private final UseCase replyCommentInCourseUseCase;
    private final ReplyModelDataMapper replyModelDataMapper;

    @Inject
    public GetReplyInCoursePresenter(UseCase replyCommentInCourseUseCase, ReplyModelDataMapper replyModelDataMapper) {
        this.replyCommentInCourseUseCase = replyCommentInCourseUseCase;
        this.replyModelDataMapper = replyModelDataMapper;
    }

    public void setView(@NonNull PostReplyInCourseFragment replyCommentInCourseActivity) {
        this.replyCommentInCourseActivity = replyCommentInCourseActivity;
    }

    public void initialize() {
        this.replyCommentInCourseUseCase.execute(new GetReplyInCoursePresenter.ReplyCommentInCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.replyCommentInCourseUseCase.unsubscribe();
    }

    private final class ReplyCommentInCourseSubscriber extends DefaultSubscriber<Reply> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetReplyInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        GetReplyInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("回复不存在");
                        break;
                    case 401:
                        GetReplyInCoursePresenter.this.replyCommentInCourseActivity.toLogin();
                        break;
                    default:
                        GetReplyInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetReplyInCoursePresenter.this.replyCommentInCourseActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Reply reply) {
            GetReplyInCoursePresenter.this.replyCommentInCourseActivity.getReply(replyModelDataMapper.transform(reply));
        }
    }
}