package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetCommentUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetCommentModule {

    private final int commentId;

    public GetCommentModule(
            int commentId
    ) {
        this.commentId = commentId;
    }

    @Provides
    @PerActivity
    UseCase provideGetCommentModuleUseCase(CommentRepository commentRepository, ThreadExecutor threadExecutor,
                                              PostExecutionThread postExecutionThread){
        return new GetCommentUseCase(commentId, commentRepository, threadExecutor, postExecutionThread);
    }
}
