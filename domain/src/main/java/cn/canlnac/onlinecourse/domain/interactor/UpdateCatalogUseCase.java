package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import rx.Observable;

/**
 * 更新目录使用用例.
 */

public class UpdateCatalogUseCase extends UseCase {

    private final int catalogId;
    private final Map<String,Object> catalog;

    private final CatalogRepository catalogRepository;

    @Inject
    public UpdateCatalogUseCase(
            int catalogId,
            Map<String,Object> catalog,
            CatalogRepository catalogRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.catalogId = catalogId;
        this.catalog = catalog;
        this.catalogRepository = catalogRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.catalogRepository.updateCatalog(catalogId,catalog);
    }
}
