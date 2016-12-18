package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.LikeChatUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class LikeChatModule {

    private final int chatId;

    public LikeChatModule(
            int chatId
    ) {
        this.chatId = chatId;
    }

    @Provides
    @PerActivity
    UseCase provideLikeChatUseCase(ChatRepository chatRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new LikeChatUseCase(chatId, chatRepository, threadExecutor, postExecutionThread);
    }
}
