package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.DeleteChatUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class DeleteChatModule {

    private final int chatId;

    public DeleteChatModule(
            int chatId
    ) {
        this.chatId = chatId;
    }

    @Provides
    @PerActivity
    UseCase provideDeleteChatUseCase(ChatRepository chatRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new DeleteChatUseCase(chatId, chatRepository, threadExecutor, postExecutionThread);
    }
}
