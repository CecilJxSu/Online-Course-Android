package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import rx.Observable;

/**
 * 获取评论列表使用用例.
 */

public class GetCommentsInChatUseCase extends UseCase {

    private final int chatId;
    private final Integer start;
    private final Integer count;
    private final String sort;

    private final ChatRepository chatRepository;

    @Inject
    public GetCommentsInChatUseCase(
            int chatId,
            Integer start,
            Integer count,
            String sort,
            ChatRepository chatRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.chatId = chatId;
        this.start = start;
        this.count = count;
        this.sort = sort;
        this.chatRepository = chatRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.chatRepository.getCommentsInChat(chatId, start,count,sort);
    }
}
