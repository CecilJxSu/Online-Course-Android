package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 获取消息列表使用用例.
 */

public class GetMessagesUseCase extends UseCase {

    private final Integer start;
    private final Integer count;
    private final Boolean isRead;

    private final UserRepository userRepository;

    @Inject
    public GetMessagesUseCase(
            Integer start,
            Integer count,
            Boolean isRead,
            UserRepository userRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.start = start;
        this.count = count;
        this.isRead = isRead;
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.getMessages(start,count,isRead);
    }
}
