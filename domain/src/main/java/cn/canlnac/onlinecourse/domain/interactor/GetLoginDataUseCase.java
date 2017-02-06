package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 获取登录信息使用用例.
 */

public class GetLoginDataUseCase extends UseCase {

    private final UserRepository userRepository;

    @Inject
    public GetLoginDataUseCase(
            UserRepository userRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.getLogin();
    }
}
