package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.DeleteCatalogUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class DeleteCatalogModule {

    private final int catalogId;

    public DeleteCatalogModule(
            int catalogId
    ) {
        this.catalogId = catalogId;
    }

    @Provides
    @PerActivity
    UseCase provideDeleteCatalogUseCase(CatalogRepository catalogRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new DeleteCatalogUseCase(catalogId, catalogRepository, threadExecutor, postExecutionThread);
    }
}
