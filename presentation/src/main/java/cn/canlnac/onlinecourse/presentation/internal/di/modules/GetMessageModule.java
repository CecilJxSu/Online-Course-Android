package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetMessageUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetMessageModule {

    private final int messageId;

    public GetMessageModule(
            int messageId
    ) {
        this.messageId = messageId;
    }

    @Provides
    @PerActivity
    UseCase provideGetMessageUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetMessageUseCase(messageId, userRepository, threadExecutor, postExecutionThread);
    }
}
