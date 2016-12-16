package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 获取别人的学习记录使用用例.
 */

public class GetOtherUserLearnRecordUseCase extends UseCase {

    private final int userId;
    private final Integer start;
    private final Integer count;

    private final UserRepository userRepository;

    @Inject
    public GetOtherUserLearnRecordUseCase(
            int userId,
            Integer start,
            Integer count,
            UserRepository userRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.userId = userId;
        this.start = start;
        this.count = count;
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.getOtherUserLearnRecord(userId,start,count);
    }
}
