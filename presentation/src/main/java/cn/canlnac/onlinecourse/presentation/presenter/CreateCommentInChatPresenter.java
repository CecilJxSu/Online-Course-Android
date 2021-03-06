package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInChatFragment;

@PerActivity
public class CreateCommentInChatPresenter implements Presenter {

    PostCommentInChatFragment createCommentInChatActivity;

    private final UseCase createCommentInChatUseCase;

    @Inject
    public CreateCommentInChatPresenter(UseCase createCommentInChatUseCase) {
        this.createCommentInChatUseCase = createCommentInChatUseCase;
    }

    public void setView(@NonNull PostCommentInChatFragment createCommentInChatActivity) {
        this.createCommentInChatActivity = createCommentInChatActivity;
    }

    public void initialize() {
        this.createCommentInChatUseCase.execute(new CreateCommentInChatPresenter.CreateCommentInChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createCommentInChatUseCase.unsubscribe();
    }

    private final class CreateCommentInChatSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        CreateCommentInChatPresenter.this.createCommentInChatActivity.showToastMessage("参数错误");
                        break;
                    case 404:
                        CreateCommentInChatPresenter.this.createCommentInChatActivity.showToastMessage("课程不存在");
                        break;
                    case 401:
                        CreateCommentInChatPresenter.this.createCommentInChatActivity.showToastMessage("未登陆");
                        CreateCommentInChatPresenter.this.createCommentInChatActivity.toLogin();
                        break;
                    default:
                        CreateCommentInChatPresenter.this.createCommentInChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                CreateCommentInChatPresenter.this.createCommentInChatActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Integer commentId) {
            CreateCommentInChatPresenter.this.createCommentInChatActivity.showToastMessage("创建成功");
            CreateCommentInChatPresenter.this.createCommentInChatActivity.postSuccess(commentId);
        }
    }
}
