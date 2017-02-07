package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.CourseList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.CourseListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseListFragment;

@PerActivity
public class GetCoursesPresenter implements Presenter {

    CourseListFragment getCoursesActivity;

    private int state;

    private final UseCase getCoursesUseCase;
    private final CourseListModelDataMapper courseListModelDataMapper;

    @Inject
    public GetCoursesPresenter(UseCase getCoursesUseCase, CourseListModelDataMapper courseListModelDataMapper) {
        this.getCoursesUseCase = getCoursesUseCase;
        this.courseListModelDataMapper = courseListModelDataMapper;
    }

    /**
     * 设置View
     * @param getCoursesActivity   view
     * @param state                0，刷新；1，加载更多
     */
    public void setView(@NonNull CourseListFragment getCoursesActivity, int state) {
        this.getCoursesActivity = getCoursesActivity;
        this.state = state;
    }

    public void initialize() {
        this.getCoursesUseCase.execute(new GetCoursesPresenter.GetCoursesSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getCoursesUseCase.unsubscribe();
    }

    private final class GetCoursesSubscriber extends DefaultSubscriber<CourseList> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        if (state == 0) {
                            GetCoursesPresenter.this.getCoursesActivity.showRefreshError("参数错误");
                        }
                        break;
                    case 404:
                        if (state == 0) {
                            GetCoursesPresenter.this.getCoursesActivity.showRefreshError("没有课程");
                        }
                        break;
                    case 401:
                        GetCoursesPresenter.this.getCoursesActivity.showRefreshError("加载失败");
                        GetCoursesPresenter.this.getCoursesActivity.toLogin();
                        break;
                    default:
                        if (state == 0) {
                            GetCoursesPresenter.this.getCoursesActivity.showRefreshError("加载失败");
                        }
                        GetCoursesPresenter.this.getCoursesActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                if (state == 0) {
                    GetCoursesPresenter.this.getCoursesActivity.showRefreshError("加载失败");
                }
                GetCoursesPresenter.this.getCoursesActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(CourseList courseList) {
            if (state == 0) {
                GetCoursesPresenter.this.getCoursesActivity.showRefreshCourses(courseListModelDataMapper.transform(courseList));
            } else if (state == 1) {
                GetCoursesPresenter.this.getCoursesActivity.showLoadMoreCourses(courseListModelDataMapper.transform(courseList));
            }
        }
    }
}