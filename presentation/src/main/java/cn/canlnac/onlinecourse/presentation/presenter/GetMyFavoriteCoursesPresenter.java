package cn.canlnac.onlinecourse.presentation.presenter;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import cn.canlnac.onlinecourse.domain.CourseList;
import cn.canlnac.onlinecourse.domain.interactor.DefaultSubscriber;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.CourseListModelDataMapper;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyFavoriteActivity;

@PerActivity
public class GetMyFavoriteCoursesPresenter implements Presenter {

    MyFavoriteActivity myFavoriteActivity;

    private int state;

    private final UseCase getMyChatsUseCase;
    private final CourseListModelDataMapper courseListModelDataMapper;

    @Inject
    public GetMyFavoriteCoursesPresenter(UseCase getMyChatsUseCase, CourseListModelDataMapper courseListModelDataMapper) {
        this.getMyChatsUseCase = getMyChatsUseCase;
        this.courseListModelDataMapper = courseListModelDataMapper;
    }

    /**
     * 设置View
     * @param myFavoriteActivity   view
     * @param state                0，刷新；1，加载更多
     */
    public void setView(@NonNull MyFavoriteActivity myFavoriteActivity, int state) {
        this.myFavoriteActivity = myFavoriteActivity;
        this.state = state;
    }

    public void initialize() {
        this.getMyChatsUseCase.execute(new GetMyFavoriteCoursesPresenter.GetMyChatsSubscriber());
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        this.getMyChatsUseCase.unsubscribe();
    }

    private final class GetMyChatsSubscriber extends DefaultSubscriber<CourseList> {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof ResponseStatusException) {
                switch (((ResponseStatusException)e).code) {
                    case 400:
                        if (state == 0) {
                            GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.showRefreshError("参数错误");
                        }
                        break;
                    case 404:
                        if (state == 0) {
                            GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.showRefreshError("没有话题");
                        }
                        break;
                    case 401:
                        GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.showRefreshError("加载失败");
                        GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.toLogin();
                        break;
                    default:
                        if (state == 0) {
                            GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.showRefreshError("加载失败");
                        }
                        GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.showToastMessage("服务器错误:"+((ResponseStatusException)e).code);
                }
            } else {
                e.printStackTrace();
                if (state == 0) {
                    GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.showRefreshError("加载失败");
                }
                GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.showToastMessage("网络连接错误");
            }
        }

        @Override
        public void onNext(CourseList courseList) {
            if (state == 0) {
                GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.showRefreshCourses(courseListModelDataMapper.transform(courseList));
            } else if (state == 1) {
                GetMyFavoriteCoursesPresenter.this.myFavoriteActivity.showLoadMoreCourses(courseListModelDataMapper.transform(courseList));
            }
        }
    }
}