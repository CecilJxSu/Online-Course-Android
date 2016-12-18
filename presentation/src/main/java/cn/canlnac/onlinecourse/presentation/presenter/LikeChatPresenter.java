package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class LikeChatPresenter implements Presenter {

    RegisterActivity likeChatActivity;

    private final UseCase likeChatUseCase;

    @Inject
    public LikeChatPresenter(UseCase likeChatUseCase) {
        this.likeChatUseCase = likeChatUseCase;
    }

    public void setView(@NonNull RegisterActivity likeChatActivity) {
        this.likeChatActivity = likeChatActivity;
    }

    public void initialize() {
        this.likeChatUseCase.execute(new LikeChatPresenter.LikeChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.likeChatUseCase.unsubscribe();
    }

    private final class LikeChatSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        LikeChatPresenter.this.likeChatActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        LikeChatPresenter.this.likeChatActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        LikeChatPresenter.this.likeChatActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        LikeChatPresenter.this.likeChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                LikeChatPresenter.this.likeChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            LikeChatPresenter.this.likeChatActivity.showToastMessage("创建成功");
        }
    }
}