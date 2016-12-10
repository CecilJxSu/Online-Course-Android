package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.RegisterUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.RegisterRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class RegisterModule {
    private final String username;
    private final String password;

    public RegisterModule(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Provides @PerActivity UseCase provideRegisterUseCase(RegisterRepository registerRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new RegisterUseCase(username, password, registerRepository, threadExecutor, postExecutionThread);
    }
}
