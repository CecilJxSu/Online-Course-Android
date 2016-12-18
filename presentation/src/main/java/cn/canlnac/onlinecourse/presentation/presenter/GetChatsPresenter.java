package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.ChatListModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetChatsPresenter implements Presenter {

    RegisterActivity getChatsActivity;

    private final UseCase getChatsUseCase;

    @Inject
    public GetChatsPresenter(UseCase getChatsUseCase) {
        this.getChatsUseCase = getChatsUseCase;
    }

    public void setView(@NonNull RegisterActivity getChatsActivity) {
        this.getChatsActivity = getChatsActivity;
    }

    public void initialize() {
        this.getChatsUseCase.execute(new GetChatsPresenter.GetChatsSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getChatsUseCase.unsubscribe();
    }

    private final class GetChatsSubscriber extends DefaultSubscriber<ChatListModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetChatsPresenter.this.getChatsActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetChatsPresenter.this.getChatsActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetChatsPresenter.this.getChatsActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetChatsPresenter.this.getChatsActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetChatsPresenter.this.getChatsActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(ChatListModel chatListModel) {
            GetChatsPresenter.this.getChatsActivity.showToastMessage("创建成功");
        }
    }
}