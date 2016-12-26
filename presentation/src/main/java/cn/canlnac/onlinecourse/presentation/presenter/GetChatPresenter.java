package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.ChatModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.ChatActivity;

@PerActivity
public class GetChatPresenter implements Presenter {

    ChatActivity createAnswerActivity;

    private final UseCase createAnswerUseCase;

    @Inject
    public GetChatPresenter(UseCase createAnswerUseCase) {
        this.createAnswerUseCase = createAnswerUseCase;
    }

    public void setView(@NonNull ChatActivity createAnswerActivity) {
        this.createAnswerActivity = createAnswerActivity;
    }

    public void initialize() {
        this.createAnswerUseCase.execute(new GetChatPresenter.GetChatSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createAnswerUseCase.unsubscribe();
    }

    private final class GetChatSubscriber extends DefaultSubscriber<ChatModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetChatPresenter.this.createAnswerActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetChatPresenter.this.createAnswerActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetChatPresenter.this.createAnswerActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetChatPresenter.this.createAnswerActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetChatPresenter.this.createAnswerActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(ChatModel chatModel) {
            GetChatPresenter.this.createAnswerActivity.showToastMessage("创建成功");
        }
    }
}