package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetMyFavoriteCoursesUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetMyFavoriteCoursesModule {

    private final Integer start;
    private final Integer count;

    public GetMyFavoriteCoursesModule(
            Integer start,
            Integer count
    ) {
        this.start = start;
        this.count = count;
    }

    @Provides
    @PerActivity
    UseCase provideGetMyFavoriteCoursesUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                             PostExecutionThread postExecutionThread){
        return new GetMyFavoriteCoursesUseCase(start, count, userRepository, threadExecutor, postExecutionThread);
    }
}
