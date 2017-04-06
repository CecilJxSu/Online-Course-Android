package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.Course;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.CourseModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;

@PerActivity
public class GetCoursePresenter implements Presenter {

    CourseActivity getCourseActivity;

    private final UseCase getCourseUseCase;
    private final CourseModelDataMapper courseModelDataMapper;

    @Inject
    public GetCoursePresenter(UseCase getCourseUseCase, CourseModelDataMapper courseModelDataMapper) {
        this.getCourseUseCase = getCourseUseCase;
        this.courseModelDataMapper = courseModelDataMapper;
    }

    public void setView(@NonNull CourseActivity getCourseActivity) {
        this.getCourseActivity = getCourseActivity;
    }

    public void initialize() {
        this.getCourseUseCase.execute(new GetCoursePresenter.GetCourseSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCourseUseCase.unsubscribe();
    }

    private final class GetCourseSubscriber extends DefaultSubscriber<Course> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        GetCoursePresenter.this.getCourseActivity.showToastMessage("课程ID错误");
                        break;
                    case 404:
                        GetCoursePresenter.this.getCourseActivity.showToastMessage("课程不存在");
                        break;
                    case 401:
                        GetCoursePresenter.this.getCourseActivity.showToastMessage("未登陆");
                        GetCoursePresenter.this.getCourseActivity.toLogin();
                        break;
                    default:
                        GetCoursePresenter.this.getCourseActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                GetCoursePresenter.this.getCourseActivity.showToastMessage("网络连接错误！");
            }
            GetCoursePresenter.this.getCourseActivity.finish();
        }

        @Override
        public void onNext(Course course) {
            if (course != null) {
                GetCoursePresenter.this.getCourseActivity.showCourse(courseModelDataMapper.transform(course));
            }
        }
    }
}