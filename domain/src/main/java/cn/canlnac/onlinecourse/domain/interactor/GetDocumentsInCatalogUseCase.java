package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import rx.Observable;

/**
 * 获取目录下的文档使用用例.
 */

public class GetDocumentsInCatalogUseCase extends UseCase {

    private final int catalogId;
    private final Integer start;
    private final Integer count;
    private final String sort;

    private final CatalogRepository catalogRepository;

    @Inject
    public GetDocumentsInCatalogUseCase(
            int catalogId,
            Integer start,
            Integer count,
            String sort,
            CatalogRepository catalogRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.catalogId = catalogId;
        this.start = start;
        this.count = count;
        this.sort = sort;
        this.catalogRepository = catalogRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.catalogRepository.getDocumentsInCatalog(catalogId, start, count, sort);
    }
}
