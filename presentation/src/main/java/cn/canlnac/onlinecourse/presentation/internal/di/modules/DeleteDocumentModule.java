package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.DeleteDocumentUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.DocumentRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class DeleteDocumentModule {

    private final int documentId;

    public DeleteDocumentModule(
            int documentId
    ) {
        this.documentId = documentId;
    }

    @Provides
    @PerActivity
    UseCase provideDeleteDocumentUseCase(DocumentRepository documentRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new DeleteDocumentUseCase(documentId, documentRepository, threadExecutor, postExecutionThread);
    }
}
