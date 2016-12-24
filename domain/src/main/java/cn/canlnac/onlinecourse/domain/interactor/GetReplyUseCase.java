package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import rx.Observable;

/**
 * 获取指定回复使用用例.
 */

public class GetReplyUseCase extends UseCase {

    private final int replyId;

    private final CommentRepository commentRepository;

    @Inject
    public GetReplyUseCase(
            int replyId,
            CommentRepository commentRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.replyId = replyId;
        this.commentRepository = commentRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.commentRepository.getReply(replyId);
    }
}
