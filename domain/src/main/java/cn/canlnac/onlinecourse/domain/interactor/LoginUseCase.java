package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 登录使用用例.
 */

public class LoginUseCase extends UseCase {

    private final String username;
    private final String password;

    private final UserRepository userRepository;

    @Inject
    public LoginUseCase(
            String username,
            String password,
            UserRepository userRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.username = username;
        this.password = password;
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.login(username, password);
    }
}
