package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.List;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetCoursesUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetCoursesModule {

    private final Integer start;
    private final Integer count;
    private final String sort;
    private final List<String> departments;

    public GetCoursesModule(
            Integer start,
            Integer count,
            String sort,
            List<String> departments
    ) {
        this.start = start;
        this.count = count;
        this.sort = sort;
        this.departments = departments;
    }

    @Provides
    @PerActivity
    UseCase provideGetCoursesUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetCoursesUseCase(start, count,sort,departments, courseRepository, threadExecutor, postExecutionThread);
    }
}
