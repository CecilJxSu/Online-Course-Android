package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetCatalogsUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetCatalogsModule {

    private final int courseId;

    public GetCatalogsModule(
            int courseId
    ) {
        this.courseId = courseId;
    }

    @Provides
    @PerActivity
    UseCase provideGetCatalogsUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread){
        return new GetCatalogsUseCase(courseId, courseRepository, threadExecutor, postExecutionThread);
    }
}
