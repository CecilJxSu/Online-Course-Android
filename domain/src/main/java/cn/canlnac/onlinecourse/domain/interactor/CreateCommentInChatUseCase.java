package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import rx.Observable;

/**
 * 创建话题评论使用用例.
 */

public class CreateCommentInChatUseCase extends UseCase {

    private final int chatId;
    private final Map<String, Object> comment;

    private final ChatRepository chatRepository;

    @Inject
    public CreateCommentInChatUseCase(
            int chatId,
            Map<String, Object> comment,
            ChatRepository chatRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.chatId = chatId;
        this.comment = comment;
        this.chatRepository = chatRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.chatRepository.createCommentInChat(chatId, comment);
    }
}
