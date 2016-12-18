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
public class GetCommentsInChatPresenter implements Presenter {

    RegisterActivity getCommentsInChatActivity;

    private final UseCase getCommentsInChatUseCase;

    @Inject
    public GetCommentsInChatPresenter(UseCase getCommentsInChatUseCase) {
        this.getCommentsInChatUseCase = getCommentsInChatUseCase;
    }

    public void setView(@NonNull RegisterActivity getCommentsInChatActivity) {
        this.getCommentsInChatActivity = getCommentsInChatActivity;
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

    private final class GetCommentsInChatSubscriber extends DefaultSubscriber<CommentListModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCommentsInChatPresenter.this.getCommentsInChatActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetCommentsInChatPresenter.this.getCommentsInChatActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetCommentsInChatPresenter.this.getCommentsInChatActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetCommentsInChatPresenter.this.getCommentsInChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetCommentsInChatPresenter.this.getCommentsInChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(CommentListModel commentListModel) {
            GetCommentsInChatPresenter.this.getCommentsInChatActivity.showToastMessage("创建成功");
        }
    }
}