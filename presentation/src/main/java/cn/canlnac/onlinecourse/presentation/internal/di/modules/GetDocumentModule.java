package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetDocumentUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.DocumentRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetDocumentModule {

    private final int documentId;

    public GetDocumentModule(
            int documentId
    ) {
        this.documentId = documentId;
    }

    @Provides
    @PerActivity
    UseCase provideGetDocumentUseCase(DocumentRepository documentRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetDocumentUseCase(documentId, documentRepository, threadExecutor, postExecutionThread);
    }
}
