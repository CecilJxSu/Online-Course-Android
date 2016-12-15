package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.RegisterRepository;
import rx.Observable;

/**
 * 获取评论列表使用用例.
 */

public class GetCommentsInChatUseCase extends UseCase {

    private final String username;
    private final String password;

    private final RegisterRepository registerRepository;

    @Inject
    public GetCommentsInChatUseCase(
            String username,
            String password,
            RegisterRepository registerRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.username = username;
        this.password = password;
        this.registerRepository = registerRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.registerRepository.register(username, password);
    }
}
