package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetCommentsInChatUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetCommentsInChatModule {

    private final int chatId;
    private final Integer start;
    private final Integer count;
    private final String sort;

    public GetCommentsInChatModule(
            int chatId,
            Integer start,
            Integer count,
            String sort
    ) {
        this.chatId = chatId;
        this.start = start;
        this.count = count;
        this.sort = sort;
    }

    @Provides
    @PerActivity
    UseCase provideGetCommentsInChatUseCase(ChatRepository chatRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetCommentsInChatUseCase(chatId, start,count,sort, chatRepository, threadExecutor, postExecutionThread);
    }
}
