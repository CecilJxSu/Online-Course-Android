package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.RegisterRepository;
import rx.Observable;

/**
 * 注册使用用例.
 */

public class RegisterUseCase extends UseCase {

    private final String username;
    private final String password;
    private final String email;

    private final RegisterRepository registerRepository;

    @Inject
    public RegisterUseCase(
            String username,
            String password,
            String email,
            RegisterRepository registerRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.username = username;
        this.password = password;
        this.email = email;
        this.registerRepository = registerRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.registerRepository.register(username, password, email);
    }
}
