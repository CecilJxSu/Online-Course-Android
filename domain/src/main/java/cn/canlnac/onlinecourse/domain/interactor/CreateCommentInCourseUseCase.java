package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 创建课程评论使用用例.
 */

public class CreateCommentInCourseUseCase extends UseCase {

    private final int courseId;
    private final Map<String, Object> comment;

    private final CourseRepository courseRepository;

    @Inject
    public CreateCommentInCourseUseCase(
            int courseId,
            Map<String, Object> comment,
            CourseRepository courseRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.courseId = courseId;
        this.comment = comment;
        this.courseRepository = courseRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.courseRepository.createCommentInCourse(courseId, comment);
    }
}
