package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 获取我的话题使用用例.
 */

public class GetMyChatsUseCase extends UseCase {

    private final Integer start;
    private final Integer count;

    private final UserRepository userRepository;

    @Inject
    public GetMyChatsUseCase(
            Integer start,
            Integer count,
            UserRepository userRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.start = start;
        this.count = count;
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.getMyChats(start,count);
    }
}
