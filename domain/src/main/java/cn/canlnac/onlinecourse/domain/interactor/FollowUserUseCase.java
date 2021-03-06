package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 关注用户使用用例.
 */

public class FollowUserUseCase extends UseCase {

    private final int userId;

    private final UserRepository userRepository;

    @Inject
    public FollowUserUseCase(
            int userId,
            UserRepository userRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.userId = userId;
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.followUser(userId);
    }
}
