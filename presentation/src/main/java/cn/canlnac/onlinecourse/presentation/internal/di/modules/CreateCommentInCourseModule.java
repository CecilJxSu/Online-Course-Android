package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.CreateCommentInCourseUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class CreateCommentInCourseModule {

    private final int courseId;
    private final Map<String, Object> comment;

    public CreateCommentInCourseModule(
            int courseId,
            Map<String, Object> comment
    ) {
        this.courseId = courseId;
        this.comment = comment;
    }

    @Provides
    @PerActivity
    UseCase provideCreateCommentInCourseUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new CreateCommentInCourseUseCase(courseId, comment, courseRepository, threadExecutor, postExecutionThread);
    }
}
