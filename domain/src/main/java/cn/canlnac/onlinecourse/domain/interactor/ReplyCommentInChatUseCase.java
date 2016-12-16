package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import rx.Observable;

/**
 * 回复评论使用用例.
 */

public class ReplyCommentInChatUseCase extends UseCase {

    private final int chatId;
    private final int commentId;
    private final Map<String,Object> reply;

    private final ChatRepository chatRepository;

    @Inject
    public ReplyCommentInChatUseCase(
            int chatId,
            int commentId,
            Map<String,Object> reply,
            ChatRepository chatRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.chatId = chatId;
        this.commentId = commentId;
        this.reply = reply;
        this.chatRepository = chatRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.chatRepository.replyCommentInChat(chatId,commentId,reply);
    }
}
