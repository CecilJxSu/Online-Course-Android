package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 回复评论使用用例.
 */

public class ReplyCommentInCourseUseCase extends UseCase {

    private final int courseId;
    private final int commentId;
    private final Map<String,Object> reply;

    private final CourseRepository courseRepository;

    @Inject
    public ReplyCommentInCourseUseCase(
            int courseId,
            int commentId,
            Map<String,Object> reply,
            CourseRepository courseRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.courseId = courseId;
        this.commentId = commentId;
        this.reply = reply;
        this.courseRepository = courseRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.courseRepository.replyCommentInCourse(courseId,commentId,reply);
    }
}
