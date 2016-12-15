package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 获取课程下的文档使用用例.
 */

public class GetDocumentsInCourseUseCase extends UseCase {

    private final int courseId;
    private final Integer start;
    private final Integer count;
    private final String sort;

    private final CourseRepository courseRepository;

    @Inject
    public GetDocumentsInCourseUseCase(
            int courseId,
            Integer start,
            Integer count,
            String sort,
            CourseRepository courseRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.courseId = courseId;
        this.start = start;
        this.count = count;
        this.sort = sort;
        this.courseRepository = courseRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.courseRepository.getDocumentsInCourse(courseId,start,count,sort);
    }
}
