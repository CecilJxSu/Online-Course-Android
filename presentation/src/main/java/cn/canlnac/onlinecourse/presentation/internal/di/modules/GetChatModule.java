package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetChatUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetChatModule {

    private final int chatId;
 
    public GetChatModule(
            int chatId
    ) {
        this.chatId = chatId;
    }

    @Provides
    @PerActivity
    UseCase provideGetChatUseCase(ChatRepository chatRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetChatUseCase(chatId, chatRepository, threadExecutor, postExecutionThread);
    }
}
