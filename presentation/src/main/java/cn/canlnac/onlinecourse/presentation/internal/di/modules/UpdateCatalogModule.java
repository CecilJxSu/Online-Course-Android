package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.UpdateCatalogUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class UpdateCatalogModule {

    private final int catalogId;
    private final Map<String,Object> catalog;

    public UpdateCatalogModule(
            int catalogId,
            Map<String,Object> catalog
    ) {
        this.catalogId = catalogId;
        this.catalog = catalog;
    }

    @Provides
    @PerActivity
    UseCase provideUpdateCatalogUseCase(CatalogRepository catalogRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread){
        return new UpdateCatalogUseCase(catalogId,catalog, catalogRepository, threadExecutor, postExecutionThread);
    }
}
