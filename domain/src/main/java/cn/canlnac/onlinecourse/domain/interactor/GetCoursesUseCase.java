package cn.canlnac.onlinecourse.domain.interactor;

import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 获取课程列表使用用例.
 */

public class GetCoursesUseCase extends UseCase {

    private final Integer start;
    private final Integer count;
    private final String sort;
    private final List<String> departments;

    private final CourseRepository courseRepository;

    @Inject
    public GetCoursesUseCase(
            Integer start,
            Integer count,
            String sort,
            List<String> departments,
            CourseRepository courseRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.start = start;
        this.count = count;
        this.sort = sort;
        this.departments = departments;
        this.courseRepository = courseRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.courseRepository.getCourses(start, count, sort, departments);
    }
}
