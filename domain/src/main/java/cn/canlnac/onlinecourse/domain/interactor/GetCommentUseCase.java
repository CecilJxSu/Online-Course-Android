package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import rx.Observable;

/**
 * 获取指定评论使用用例.
 */

public class GetCommentUseCase extends UseCase {

    private final int commentId;

    private final CommentRepository commentRepository;

    @Inject
    public GetCommentUseCase(
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
        return this.commentRepository.getComment(commentId);
    }
}
