package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetFollowerUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetFollowerModule {

    private final Integer start;
    private final Integer count;

    public GetFollowerModule(
            Integer start,
            Integer count
    ) {
        this.start = start;
        this.count = count;
    }

    @Provides
    @PerActivity
    UseCase provideGetFollowerUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetFollowerUseCase(start, count, userRepository, threadExecutor, postExecutionThread);
    }
}
