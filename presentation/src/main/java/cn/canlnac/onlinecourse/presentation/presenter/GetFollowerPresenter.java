package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.FollowerModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetFollowerPresenter implements Presenter {

    RegisterActivity createAnswerActivity;

    private final UseCase createAnswerUseCase;

    @Inject
    public GetFollowerPresenter(UseCase createAnswerUseCase) {
        this.createAnswerUseCase = createAnswerUseCase;
    }

    public void setView(@NonNull RegisterActivity createAnswerActivity) {
        this.createAnswerActivity = createAnswerActivity;
    }

    public void initialize() {
        this.createAnswerUseCase.execute(new GetFollowerPresenter.GetFollowerSubscriber());
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

    private final class GetFollowerSubscriber extends DefaultSubscriber<FollowerModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetFollowerPresenter.this.createAnswerActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetFollowerPresenter.this.createAnswerActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetFollowerPresenter.this.createAnswerActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetFollowerPresenter.this.createAnswerActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetFollowerPresenter.this.createAnswerActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(FollowerModel followerModel) {
            GetFollowerPresenter.this.createAnswerActivity.showToastMessage("创建成功");
        }
    }
}