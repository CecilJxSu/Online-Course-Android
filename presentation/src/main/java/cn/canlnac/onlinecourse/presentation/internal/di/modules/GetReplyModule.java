package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetReplyUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetReplyModule {

    private final int replyId;

    public GetReplyModule(
            int replyId
    ) {
        this.replyId = replyId;
    }

    @Provides
    @PerActivity
    UseCase provideGetReplyModuleUseCase(CommentRepository commentRepository, ThreadExecutor threadExecutor,
                                              PostExecutionThread postExecutionThread){
        return new GetReplyUseCase(replyId, commentRepository, threadExecutor, postExecutionThread);
    }
}
