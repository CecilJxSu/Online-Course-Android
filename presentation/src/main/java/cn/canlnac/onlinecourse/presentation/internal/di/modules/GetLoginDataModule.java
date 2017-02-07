package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetLoginDataUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetLoginDataModule {

    public GetLoginDataModule() {}

    @Provides
    @PerActivity
    UseCase provideGetLoginDataUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetLoginDataUseCase(userRepository, threadExecutor, postExecutionThread);
    }
}
