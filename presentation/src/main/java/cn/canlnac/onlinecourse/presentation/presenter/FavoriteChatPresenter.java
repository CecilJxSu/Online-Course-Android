package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class FavoriteChatPresenter implements Presenter {

    RegisterActivity favoriteChatActivity;

    private final UseCase favoriteChatUseCase;

    @Inject
    public FavoriteChatPresenter(UseCase favoriteChatUseCase) {
        this.favoriteChatUseCase = favoriteChatUseCase;
    }

    public void setView(@NonNull RegisterActivity favoriteChatActivity) {
        this.favoriteChatActivity = favoriteChatActivity;
    }

    public void initialize() {
        this.favoriteChatUseCase.execute(new FavoriteChatPresenter.FavoriteChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.favoriteChatUseCase.unsubscribe();
    }

    private final class FavoriteChatSubscriber extends DefaultSubscriber<Void> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        FavoriteChatPresenter.this.favoriteChatActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        FavoriteChatPresenter.this.favoriteChatActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        FavoriteChatPresenter.this.favoriteChatActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        FavoriteChatPresenter.this.favoriteChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                FavoriteChatPresenter.this.favoriteChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Void empty) {
            FavoriteChatPresenter.this.favoriteChatActivity.showToastMessage("创建成功");
        }
    }
}