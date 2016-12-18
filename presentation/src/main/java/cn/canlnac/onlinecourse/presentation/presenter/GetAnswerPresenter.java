package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.AnswerModel;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class GetAnswerPresenter implements Presenter {

    RegisterActivity getAnswerActivity;

    private final UseCase getAnswerUseCase;

    @Inject
    public GetAnswerPresenter(UseCase getAnswerUseCase) {
        this.getAnswerUseCase = getAnswerUseCase;
    }

    public void setView(@NonNull RegisterActivity getAnswerActivity) {
        this.getAnswerActivity = getAnswerActivity;
    }

    public void initialize() {
        this.getAnswerUseCase.execute(new GetAnswerPresenter.GetAnswerSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getAnswerUseCase.unsubscribe();
    }

    private final class GetAnswerSubscriber extends DefaultSubscriber<AnswerModel> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetAnswerPresenter.this.getAnswerActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        GetAnswerPresenter.this.getAnswerActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        GetAnswerPresenter.this.getAnswerActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        GetAnswerPresenter.this.getAnswerActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                GetAnswerPresenter.this.getAnswerActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(AnswerModel answerModel) {
            GetAnswerPresenter.this.getAnswerActivity.showToastMessage("创建成功");
        }
    }
}