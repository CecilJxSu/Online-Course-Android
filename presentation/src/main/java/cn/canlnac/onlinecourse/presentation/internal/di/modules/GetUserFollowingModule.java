package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetUserFollowingUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetUserFollowingModule {

    private final int userId;
    private final Integer start;
    private final Integer count;

    public GetUserFollowingModule(
            int userId,
            Integer start,
            Integer count
    ) {
        this.userId = userId;
        this.start = start;
        this.count = count;
    }

    @Provides
    @PerActivity
    UseCase provideGetUserFollowingUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetUserFollowingUseCase(userId, start,count, userRepository, threadExecutor, postExecutionThread);
    }
}
