package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.MessageListModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetMessagesPresenter implements Presenter {

    RegisterActivity getMessagesActivity;

    private final UseCase getMessagesUseCase;

    @Inject
    public GetMessagesPresenter(UseCase getMessagesUseCase) {
        this.getMessagesUseCase = getMessagesUseCase;
    }

    public void setView(@NonNull RegisterActivity getMessagesActivity) {
        this.getMessagesActivity = getMessagesActivity;
    }

    public void initialize() {
        this.getMessagesUseCase.execute(new GetMessagesPresenter.GetMessagesSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getMessagesUseCase.unsubscribe();
    }

    private final class GetMessagesSubscriber extends DefaultSubscriber<MessageListModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetMessagesPresenter.this.getMessagesActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetMessagesPresenter.this.getMessagesActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetMessagesPresenter.this.getMessagesActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetMessagesPresenter.this.getMessagesActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetMessagesPresenter.this.getMessagesActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(MessageListModel messageListModel) {
            GetMessagesPresenter.this.getMessagesActivity.showToastMessage("创建成功");
        }
    }
}