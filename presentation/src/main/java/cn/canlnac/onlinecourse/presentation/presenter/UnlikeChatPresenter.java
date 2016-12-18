package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class UnlikeChatPresenter implements Presenter {

    RegisterActivity unlikeChatActivity;

    private final UseCase unlikeChatUseCase;

    @Inject
    public UnlikeChatPresenter(UseCase unlikeChatUseCase) {
        this.unlikeChatUseCase = unlikeChatUseCase;
    }

    public void setView(@NonNull RegisterActivity unlikeChatActivity) {
        this.unlikeChatActivity = unlikeChatActivity;
    }

    public void initialize() {
        this.unlikeChatUseCase.execute(new UnlikeChatPresenter.UnlikeChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.unlikeChatUseCase.unsubscribe();
    }

    private final class UnlikeChatSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            UnlikeChatPresenter.this.unlikeChatActivity.showToastMessage("创建成功");
        }
    }
}