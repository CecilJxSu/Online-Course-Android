package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 获取课程列表用例.
 */

public class GetCourseList extends UseCase {
    private final CourseRepository courseRepository;

    @Inject
    public GetCourseList(
            CourseRepository courseRepository,
             ThreadExecutor threadExecutor,
             PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);
        this.courseRepository = courseRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.courseRepository.courses();
    }
}
