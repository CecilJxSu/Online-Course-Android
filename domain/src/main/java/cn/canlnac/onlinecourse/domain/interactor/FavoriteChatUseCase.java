package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import rx.Observable;

/**
 * 收藏话题使用用例.
 */

public class FavoriteChatUseCase extends UseCase {

    private final int chatId;

    private final ChatRepository chatRepository;

    @Inject
    public FavoriteChatUseCase(
            int chatId,
            ChatRepository chatRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.chatId = chatId;
        this.chatRepository = chatRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.chatRepository.favoriteChat(chatId);
    }
}
