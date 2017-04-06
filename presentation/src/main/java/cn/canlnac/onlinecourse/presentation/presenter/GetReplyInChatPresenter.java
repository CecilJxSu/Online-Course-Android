package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Reply;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.ReplyModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostReplyInChatFragment;

@PerActivity
public class GetReplyInChatPresenter implements Presenter {

    PostReplyInChatFragment replyCommentInChatActivity;

    private final UseCase replyCommentInChatUseCase;
    private final ReplyModelDataMapper replyModelDataMapper;

    @Inject
    public GetReplyInChatPresenter(UseCase replyCommentInChatUseCase, ReplyModelDataMapper replyModelDataMapper) {
        this.replyCommentInChatUseCase = replyCommentInChatUseCase;
        this.replyModelDataMapper = replyModelDataMapper;
    }

    public void setView(@NonNull PostReplyInChatFragment replyCommentInChatActivity) {
        this.replyCommentInChatActivity = replyCommentInChatActivity;
    }

    public void initialize() {
        this.replyCommentInChatUseCase.execute(new GetReplyInChatPresenter.ReplyCommentInChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.replyCommentInChatUseCase.unsubscribe();
    }

    private final class ReplyCommentInChatSubscriber extends DefaultSubscriber<Reply> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetReplyInChatPresenter.this.replyCommentInChatActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        GetReplyInChatPresenter.this.replyCommentInChatActivity.showToastMessage("回复不存在");
                        break;
                    case 401:
                        GetReplyInChatPresenter.this.replyCommentInChatActivity.toLogin();
                        break;
                    default:
                        GetReplyInChatPresenter.this.replyCommentInChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetReplyInChatPresenter.this.replyCommentInChatActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Reply reply) {
            GetReplyInChatPresenter.this.replyCommentInChatActivity.getReply(replyModelDataMapper.transform(reply));
        }
    }
}