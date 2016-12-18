package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetDocumentsInCatalogUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetDocumentsInCatalogModule {

    private final int catalogId;
    private final Integer start;
    private final Integer count;
    private final String sort;

    public GetDocumentsInCatalogModule(
            int catalogId,
            Integer start,
            Integer count,
            String sort
    ) {
        this.catalogId = catalogId;
        this.start = start;
        this.count = count;
        this.sort = sort;
    }

    @Provides
    @PerActivity
    UseCase provideGetDocumentsInCatalogUseCase(CatalogRepository catalogRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetDocumentsInCatalogUseCase(catalogId, start,count,sort, catalogRepository, threadExecutor, postExecutionThread);
    }
}
