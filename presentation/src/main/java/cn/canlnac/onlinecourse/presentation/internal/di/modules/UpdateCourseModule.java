package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 更新课程使用用例.
 */

public class UpdateCourseModule extends UseCase {

    private final int courseId;
    private final Map<String,String> course;

    private final CourseRepository courseRepository;

    @Inject
    public UpdateCourseModule(
            int courseId,
            Map<String,String> course,
            CourseRepository courseRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.courseId = courseId;
        this.course = course;
        this.courseRepository = courseRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.courseRepository.updateCourse(courseId, course);
    }
}
