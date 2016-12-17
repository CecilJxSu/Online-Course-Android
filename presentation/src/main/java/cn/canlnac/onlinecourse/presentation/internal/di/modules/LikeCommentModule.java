package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.LikeCommentUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class LikeCommentModule {

    private final int commentId;

    public LikeCommentModule(
            int commentId
    ) {
        this.commentId = commentId;
    }

    @Provides
    @PerActivity
    UseCase provideLikeCommentUseCase(CommentRepository commentRepository, ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread){
        return new LikeCommentUseCase(commentId, commentRepository, threadExecutor, postExecutionThread);
    }
}
