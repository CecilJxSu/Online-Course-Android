package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetChatsChatUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetChatsChatModule {

    private final Integer start;
    private final Integer count;
    private final String sort;

    public GetChatsChatModule(
            Integer start,
            Integer count,
            String sort
    ) {
        this.start = start;
        this.count = count;
        this.sort = sort;
    }

    @Provides
    @PerActivity
    UseCase provideGetChatsChatUseCase(ChatRepository chatRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetChatsChatUseCase(start, count,sort, chatRepository, threadExecutor, postExecutionThread);
    }
}
