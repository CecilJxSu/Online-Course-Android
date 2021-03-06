package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetMessagesUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetMessagesInMessageViewModule {

    private final Integer start;
    private final Integer count;
    private final Boolean isRead;

    public GetMessagesInMessageViewModule(
            Integer start,
            Integer count,
            Boolean isRead
    ) {
        this.start = start;
        this.count = count;
        this.isRead = isRead;
    }

    @Provides
    @PerActivity
    UseCase provideGetMessagesUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetMessagesUseCase(start, count,isRead, userRepository, threadExecutor, postExecutionThread);
    }
}
