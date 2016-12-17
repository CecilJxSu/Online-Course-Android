package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.CreateCourseUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class CreateCourseModule {

    private final Map<String, String> course;

    public CreateCourseModule(
            Map<String, String> course
    ) {
        this.course = course;
    }

    @Provides
    @PerActivity
    UseCase provideCreateCourseUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread){
        return new CreateCourseUseCase(course, courseRepository, threadExecutor, postExecutionThread);
    }
}
