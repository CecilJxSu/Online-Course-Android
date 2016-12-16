package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.DocumentRepository;
import rx.Observable;

/**
 * 删除文档使用用例.
 */

public class DeleteDocumentUseCase extends UseCase {

    private final int documentId;

    private final DocumentRepository documentRepository;

    @Inject
    public DeleteDocumentUseCase(
            int documentId,
            DocumentRepository documentRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.documentId = documentId;
        this.documentRepository = documentRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.documentRepository.deleteDocument(documentId);
    }
}
