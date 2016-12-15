package cn.canlnac.onlinecourse.domain.interactor;

import com.sun.istack.internal.Nullable;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import rx.Observable;

/**
 * 获取话题列表使用用例.
 */

public class GetChatsChatUseCase extends UseCase {

    private final Integer start;
    private final Integer count;
    private final String sort;

    private final ChatRepository chatRepository;

    @Inject
    public GetChatsChatUseCase(
            @Nullable Integer start,
            @Nullable Integer count,
            @Nullable String sort,
            ChatRepository chatRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.start = start;
        this.count = count;
        this.sort = sort;
        this.chatRepository = chatRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.chatRepository.getChats(start, count,sort);
    }
}
