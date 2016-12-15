package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 取消收藏使用用例.
 */

public class UnfavoriteCourseUseCase extends UseCase {

    private final int courseId;

    private final CourseRepository courseRepository;

    @Inject
    public UnfavoriteCourseUseCase(
            int courseId,
            CourseRepository courseRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.courseId = courseId;
        this.courseRepository = courseRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.courseRepository.unfavoriteCourse(courseId);
    }
}
