package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 更新用户资料使用用例.
 */

public class UpdateUserProfileUseCase extends UseCase {

    private final Map<String,String> profile;

    private final UserRepository userRepository;

    @Inject
    public UpdateUserProfileUseCase(
            Map<String,String> profile,
            UserRepository userRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.profile = profile;
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.updateUserProfile(profile);
    }
}
