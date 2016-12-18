package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.ReplyCommentInCourseUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class ReplyCommentInCourseModule {

    private final int courseId;
    private final int commentId;
    private final Map<String,Object> reply;

    public ReplyCommentInCourseModule(
            int courseId,
            int commentId,
            Map<String,Object> reply
    ) {
        this.courseId = courseId;
        this.commentId = commentId;
        this.reply = reply;
    }

    @Provides
    @PerActivity
    UseCase provideReplyCommentInCourseUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                               PostExecutionThread postExecutionThread){
        return new ReplyCommentInCourseUseCase(courseId, commentId,reply, courseRepository, threadExecutor, postExecutionThread);
    }
}
