package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class DeleteChatPresenter implements Presenter {

    RegisterActivity deleteChatActivity;

    private final UseCase deleteChatUseCase;

    @Inject
    public DeleteChatPresenter(UseCase deleteChatUseCase) {
        this.deleteChatUseCase = deleteChatUseCase;
    }

    public void setView(@NonNull RegisterActivity deleteChatActivity) {
        this.deleteChatActivity = deleteChatActivity;
    }

    public void initialize() {
        this.deleteChatUseCase.execute(new DeleteChatPresenter.DeleteChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.deleteChatUseCase.unsubscribe();
    }

    private final class DeleteChatSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        DeleteChatPresenter.this.deleteChatActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        DeleteChatPresenter.this.deleteChatActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        DeleteChatPresenter.this.deleteChatActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        DeleteChatPresenter.this.deleteChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                DeleteChatPresenter.this.deleteChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            DeleteChatPresenter.this.deleteChatActivity.showToastMessage("创建成功");
        }
    }
}