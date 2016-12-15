package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 删除消息使用用例.
 */

public class DeleteMessageUseCase extends UseCase {

    private final int messageId;

    private final UserRepository userRepository;

    @Inject
    public DeleteMessageUseCase(
            int messageId,
            String password,
            UserRepository userRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.messageId = messageId;
        this.userRepository = userRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.userRepository.deleteMessage(messageId);
    }
}
