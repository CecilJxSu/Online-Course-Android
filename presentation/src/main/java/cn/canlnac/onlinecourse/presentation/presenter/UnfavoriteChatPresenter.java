package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UnfavoriteChatPresenter implements Presenter {

    RegisterActivity unfavoriteChatActivity;

    private final UseCase unfavoriteChatUseCase;

    @Inject
    public UnfavoriteChatPresenter(UseCase unfavoriteChatUseCase) {
        this.unfavoriteChatUseCase = unfavoriteChatUseCase;
    }

    public void setView(@NonNull RegisterActivity unfavoriteChatActivity) {
        this.unfavoriteChatActivity = unfavoriteChatActivity;
    }

    public void initialize() {
        this.unfavoriteChatUseCase.execute(new UnfavoriteChatPresenter.UnfavoriteChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.unfavoriteChatUseCase.unsubscribe();
    }

    private final class UnfavoriteChatSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnfavoriteChatPresenter.this.unfavoriteChatActivity.showToastMessage("创建成功");
        }
    }
}