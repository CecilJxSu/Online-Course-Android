package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.PostChatActivity;

@PerActivity
public class CreateChatPresenter implements Presenter {

    PostChatActivity createChatActivity;

    private final UseCase createChatUseCase;

    @Inject
    public CreateChatPresenter(UseCase createChatUseCase) {
        this.createChatUseCase = createChatUseCase;
    }

    public void setView(@NonNull PostChatActivity createChatActivity) {
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
                        CreateChatPresenter.this.createChatActivity.showToastMessage("参数错误");
                        break;
                    case 401:
                        CreateChatPresenter.this.createChatActivity.showToastMessage("未登陆");
                        CreateChatPresenter.this.createChatActivity.toLogin();
                        break;
                    default:
                        CreateChatPresenter.this.createChatActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                CreateChatPresenter.this.createChatActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(Integer chatId) {
            CreateChatPresenter.this.createChatActivity.showToastMessage("创建成功");
            CreateChatPresenter.this.createChatActivity.finish();
        }
    }
}
