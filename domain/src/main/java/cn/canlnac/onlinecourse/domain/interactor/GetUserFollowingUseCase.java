package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 获取用户关注使用用例.
 */

public class GetUserFollowingUseCase extends UseCase {

    private final int userId;
    private final Integer start;
    private final Integer count;

    private final UserRepository userRepository;

    @Inject
    public GetUserFollowingUseCase(
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
        return this.userRepository.getUserFollowing(userId,start,count);
    }
}
