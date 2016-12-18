package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetUserProfileUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetUserProfileModule {

    private final int userId;

    public GetUserProfileModule(
            int userId
    ) {
        this.userId = userId;
    }

    @Provides
    @PerActivity
    UseCase provideGetUserProfileUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetUserProfileUseCase(userId, userRepository, threadExecutor, postExecutionThread);
    }
}
