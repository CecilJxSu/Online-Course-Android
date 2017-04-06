package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.SetLoginDataUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.mapper.LoginModelDataMapper;
import cn.canlnac.onlinecourse.presentation.model.LoginModel;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class SetLoginDataModule {

    private LoginModel loginModel;
    private LoginModelDataMapper loginModelDataMapper;
    public SetLoginDataModule(LoginModel loginModel) {
        this.loginModel = loginModel;
        this.loginModelDataMapper = new LoginModelDataMapper();
    }

    @Provides
    @PerActivity
    UseCase provideGetLoginDataUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new SetLoginDataUseCase(loginModelDataMapper.transform(loginModel),userRepository, threadExecutor, postExecutionThread);
    }
}
