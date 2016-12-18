package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetCommentsInCourseUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetCommentsInCourseModule {

    private final int courseId;
    private final Integer start;
    private final Integer count;
    private final String sort;

    public GetCommentsInCourseModule(
            int courseId,
            Integer start,
            Integer count,
            String sort
    ) {
        this.courseId = courseId;
        this.start = start;
        this.count = count;
        this.sort = sort;
    }

    @Provides
    @PerActivity
    UseCase provideGetCommentsInCourseUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetCommentsInCourseUseCase(courseId, start,count,sort, courseRepository, threadExecutor, postExecutionThread);
    }
}
