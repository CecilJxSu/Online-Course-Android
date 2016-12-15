package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import rx.Observable;

/**
 * 创建目录文件使用用例.
 */

public class CreateDocumentInCatalogUseCase extends UseCase {

    private final int catalogId;
    private final Map<String,Object> document;

    private final CatalogRepository catalogRepository;

    @Inject
    public CreateDocumentInCatalogUseCase(
            int catalogId,
            Map<String,Object> document,
            CatalogRepository catalogRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.catalogId = catalogId;
        this.document = document;
        this.catalogRepository = catalogRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.catalogRepository.createDocumentInCatalog(catalogId, document);
    }
}
