package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.CommentList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.CommentListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.CommentActivity;

@PerActivity
public class GetCommentsInChatPresenter implements Presenter {

    CommentActivity getCommentsInChatActivity;

    private final UseCase getCommentsInChatUseCase;
    private final CommentListModelDataMapper commentListModelDataMapper;

    private int state;

    @Inject
    public GetCommentsInChatPresenter(UseCase getCommentsInChatUseCase, CommentListModelDataMapper commentListModelDataMapper) {
        this.getCommentsInChatUseCase = getCommentsInChatUseCase;
        this.commentListModelDataMapper = commentListModelDataMapper;
    }

    public void setView(@NonNull CommentActivity getCommentsInChatActivity, int state) {
        this.getCommentsInChatActivity = getCommentsInChatActivity;
        this.state = state;
    }

    public void initialize() {
        this.getCommentsInChatUseCase.execute(new GetCommentsInChatPresenter.GetCommentsInChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCommentsInChatUseCase.unsubscribe();
    }

    private final class GetCommentsInChatSubscriber extends DefaultSubscriber<CommentList> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        if (state == 0) {
                            GetCommentsInChatPresenter.this.getCommentsInChatActivity.showRefreshError("参数错误");
                        }
                        break;
                    case 404:
                        if (state == 0) {
                            GetCommentsInChatPresenter.this.getCommentsInChatActivity.showRefreshError("没有评论");
                        }
                        break;
                    default:
                        if (state == 0) {
                            GetCommentsInChatPresenter.this.getCommentsInChatActivity.showRefreshError("加载失败");
                        }
                        GetCommentsInChatPresenter.this.getCommentsInChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                if (state == 0) {
                    GetCommentsInChatPresenter.this.getCommentsInChatActivity.showRefreshError("加载失败");
                }
                GetCommentsInChatPresenter.this.getCommentsInChatActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(CommentList commentList) {
            if (state == 0) {
                GetCommentsInChatPresenter.this.getCommentsInChatActivity.showRefreshComments(commentListModelDataMapper.transform(commentList));
            } else if (state == 1) {
                GetCommentsInChatPresenter.this.getCommentsInChatActivity.showLoadMoreComments(commentListModelDataMapper.transform(commentList));
            }
        }
    }
}