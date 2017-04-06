package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.LoginUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class LoginModule {

    private final String username;
    private final String password;

    public LoginModule(
            String username,
            String password
    ) {
        this.username = username;
        this.password = password;
    }

    @Provides
    @PerActivity
    UseCase provideLoginUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new LoginUseCase(username, password, userRepository, threadExecutor, postExecutionThread);
    }
}
