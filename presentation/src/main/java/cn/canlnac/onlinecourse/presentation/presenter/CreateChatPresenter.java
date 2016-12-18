package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class CreateChatPresenter implements Presenter {

    RegisterActivity createChatActivity;

    private final UseCase createChatUseCase;

    @Inject
    public CreateChatPresenter(UseCase createChatUseCase) {
        this.createChatUseCase = createChatUseCase;
    }

    public void setView(@NonNull RegisterActivity createChatActivity) {
        this.createChatActivity = createChatActivity;
    }

    public void initialize() {
        this.createChatUseCase.execute(new CreateChatPresenter.CreateChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createChatUseCase.unsubscribe();
    }

    private final class CreateChatSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        CreateChatPresenter.this.createChatActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        CreateChatPresenter.this.createChatActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        CreateChatPresenter.this.createChatActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        CreateChatPresenter.this.createChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                CreateChatPresenter.this.createChatActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer chatId) {
            CreateChatPresenter.this.createChatActivity.showToastMessage("注册成功");
        }
    }
}
