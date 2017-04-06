package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.CreateChatUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class CreateChatModule {

    private final Map<String, Object> chat;

    public CreateChatModule(
            Map<String, Object> chat
    ) {
        this.chat = chat;
    }

    @Provides
    @PerActivity
    UseCase provideCreateChatUseCase(ChatRepository chatRepository, ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread){
        return new CreateChatUseCase(chat, chatRepository, threadExecutor, postExecutionThread);
    }
}
