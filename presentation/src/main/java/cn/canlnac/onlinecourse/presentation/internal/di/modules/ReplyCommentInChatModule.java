package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.ReplyCommentInChatUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class ReplyCommentInChatModule {

    private final int chatId;
    private final int commentId;
    private final Map<String,Object> reply;

    public ReplyCommentInChatModule(
            int chatId,
            int commentId,
            Map<String,Object> reply
    ) {
        this.chatId = chatId;
        this.commentId = commentId;
        this.reply = reply;
    }

    @Provides
    @PerActivity
    UseCase provideReplyCommentInChatUseCase(ChatRepository chatRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new ReplyCommentInChatUseCase(chatId, commentId,reply, chatRepository, threadExecutor, postExecutionThread);
    }
}
