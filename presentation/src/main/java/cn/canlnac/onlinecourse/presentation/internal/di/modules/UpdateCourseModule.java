package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.UpdateCourseUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class UpdateCourseModule {

    private final int courseId;
    private final Map<String,String> course;

    public UpdateCourseModule(
            int courseId,
            Map<String,String> course
    ) {
        this.courseId = courseId;
        this.course = course;
    }

    @Provides
    @PerActivity
    UseCase provideUpdateCourseUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread){
        return new UpdateCourseUseCase(courseId,course, courseRepository, threadExecutor, postExecutionThread);
    }
}
