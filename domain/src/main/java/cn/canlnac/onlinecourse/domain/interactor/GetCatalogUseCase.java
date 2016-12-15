package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import rx.Observable;

/**
 * 获取目录使用用例.
 */

public class GetCatalogUseCase extends UseCase {

    private final int catalogId;

    private final CatalogRepository catalogRepository;

    @Inject
    public GetCatalogUseCase(
            int catalogId,
            CatalogRepository catalogRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.catalogId = catalogId;
        this.catalogRepository = catalogRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.catalogRepository.getCatalog(catalogId);
    }
}
