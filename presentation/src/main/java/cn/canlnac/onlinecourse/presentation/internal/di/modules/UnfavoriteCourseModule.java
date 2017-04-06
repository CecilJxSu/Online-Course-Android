package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.UnfavoriteCourseUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class UnfavoriteCourseModule {

    private final int courseId;

    public UnfavoriteCourseModule(
            int courseId
    ) {
        this.courseId = courseId;
    }

    @Provides
    @PerActivity
    UseCase provideUnfavoriteCourseUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                           PostExecutionThread postExecutionThread){
        return new UnfavoriteCourseUseCase(courseId, courseRepository, threadExecutor, postExecutionThread);
    }
}
