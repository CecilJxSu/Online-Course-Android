package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.CreateCommentInChatUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class CreateCommentInChatModule {

    private final int chatId;
    private final Map<String, Object> comment;

    public CreateCommentInChatModule(
            int chatId,
            Map<String, Object> comment
    ) {
        this.chatId = chatId;
        this.comment = comment;
    }

    @Provides
    @PerActivity
    UseCase provideCreateCommentInChatUseCase(ChatRepository chatRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new CreateCommentInChatUseCase(chatId, comment, chatRepository, threadExecutor, postExecutionThread);
    }
}
