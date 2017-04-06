package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.CreateCatalogUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class CreateCatalogModule {

    private final int courseId;
    private final Map<String, Object> catalog;

    public CreateCatalogModule(
            int courseId,
            Map<String, Object> catalog
    ) {
        this.courseId = courseId;
        this.catalog = catalog;
    }

    @Provides
    @PerActivity
    UseCase provideCreateCatalogUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new CreateCatalogUseCase(courseId, catalog, courseRepository, threadExecutor, postExecutionThread);
    }
}
