package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

@PerActivity
public class CreateQuestionPresenter implements Presenter {

    RegisterActivity createQuestionActivity;

    private final UseCase createQuestionUseCase;

    @Inject
    public CreateQuestionPresenter(UseCase createQuestionUseCase) {
        this.createQuestionUseCase = createQuestionUseCase;
    }

    public void setView(@NonNull RegisterActivity createQuestionActivity) {
        this.createQuestionActivity = createQuestionActivity;
    }

    public void initialize() {
        this.createQuestionUseCase.execute(new CreateQuestionPresenter.CreateQuestionSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.createQuestionUseCase.unsubscribe();
    }

    private final class CreateQuestionSubscriber extends DefaultSubscriber<Integer> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        CreateQuestionPresenter.this.createQuestionActivity.showToastMessage("参数错误！");
                        break;
                    case 404:
                        CreateQuestionPresenter.this.createQuestionActivity.showToastMessage("资源不存在！");
                        break;
                    case 409:
                        CreateQuestionPresenter.this.createQuestionActivity.showToastMessage("用户名已被注册！");
                        break;
                    default:
                        CreateQuestionPresenter.this.createQuestionActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                CreateQuestionPresenter.this.createQuestionActivity.showToastMessage("网络连接错误！");
            }
        }

        @Override
        public void onNext(Integer questionId) {
            CreateQuestionPresenter.this.createQuestionActivity.showToastMessage("创建成功");
        }
    }
}