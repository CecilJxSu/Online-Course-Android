package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import rx.Observable;

/**
 * 取消点赞使用用例.
 */

public class UnlikeCommentUseCase extends UseCase {

    private final int commentId;

    private final CommentRepository commentRepository;

    @Inject
    public UnlikeCommentUseCase(
            int commentId,
            CommentRepository commentRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.commentId = commentId;
        this.commentRepository = commentRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.commentRepository.unlikeComment(commentId);
    }
}
