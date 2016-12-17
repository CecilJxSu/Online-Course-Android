package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.UpdateUserProfileUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class UpdateUserProfileModule {

    private final Map<String,String> profile;

    public UpdateUserProfileModule(
            Map<String,String> profile
    ) {
        this.profile = profile;
    }

    @Provides
    @PerActivity
    UseCase provideUpdateUserProfileUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread){
        return new UpdateUserProfileUseCase(profile, userRepository, threadExecutor, postExecutionThread);
    }
}
