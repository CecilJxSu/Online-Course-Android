package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.UnfavoriteChatUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class UnfavoriteChatModule {

    private final int chatId;

    public UnfavoriteChatModule(
            int chatId
    ) {
        this.chatId = chatId;
    }

    @Provides
    @PerActivity
    UseCase provideUnfavoriteChatUseCase(ChatRepository chatRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new UnfavoriteChatUseCase(chatId, chatRepository, threadExecutor, postExecutionThread);
    }
}
