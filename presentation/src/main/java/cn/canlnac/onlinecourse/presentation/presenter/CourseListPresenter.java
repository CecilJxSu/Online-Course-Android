package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import cn.canlnac.onlinecourse.domain.exception.DefaultErrorBundle;
import cn.canlnac.onlinecourse.domain.exception.ErrorBundle;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.exception.ErrorMessageFactory;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.CourseModelMapper;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;
import cn.canlnac.onlinecourse.presentation.ui.view.CourseListView;

/**
 * 课程列表.
 */
@PerActivity
public class CourseListPresenter implements Presenter {
    private CourseListView courseListView;

    private final UseCase getCourseListCase;
    private final CourseModelMapper courseModelMapper;

    @Inject
    public CourseListPresenter(
        @Named("courseList") UseCase getCourseListCase,
        CourseModelMapper courseModelMapper
    ) {
        this.getCourseListCase = getCourseListCase;
        this.courseModelMapper = courseModelMapper;
    }

    /**
     * 设置视图
     * @param courseListView
     */
    public void setView(@NonNull CourseListView courseListView) {
        this.courseListView = courseListView;
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    /**
     * 取消订阅事件
     */
    @Override
    public void destory() {
        this.getCourseListCase.unsubscribe();
        this.courseListView = null;
    }

    /**
     * 初始化presenter，开始获取课程列表数据
     */
    public void initialize() {
        this.loadCourseList();
    }

    /**
     * 加载所有课程列表
     */
    private void loadCourseList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getCourseList();
    }

    private void showViewLoading() {
        this.courseListView.showLoading();
    }

    private void hideViewLoading() {
        this.courseListView.hideLoading();
    }

    private void showViewRetry() {
        this.courseListView.showRetry();
    }

    private void hideViewRetry() {
        this.courseListView.hideRetry();
    }

    private void getCourseList() {
        this.getCourseListCase.execute(new CourseListSubscriber());
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.courseListView.context(), errorBundle.getException());
        this.courseListView.showError(errorMessage);
    }

    private final class CourseListSubscriber extends DefaultSubscriber<List<CourseModel>> {
        @Override
        public void onCompleted() {
            CourseListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            CourseListPresenter.this.hideViewLoading();
            CourseListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
            CourseListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<CourseModel> courseModels) {
            CourseListPresenter.this.getCourseList();
        }
    }
}
