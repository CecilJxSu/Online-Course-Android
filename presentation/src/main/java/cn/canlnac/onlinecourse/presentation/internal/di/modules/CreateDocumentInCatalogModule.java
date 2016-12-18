package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.CreateDocumentInCatalogUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class CreateDocumentInCatalogModule {

    private final int catalogId;
    private final Map<String,Object> document;

    public CreateDocumentInCatalogModule(
            int catalogId,
            Map<String,Object> document
    ) {
        this.catalogId = catalogId;
        this.document = document;
    }

    @Provides
    @PerActivity
    UseCase provideCreateDocumentInCatalogUseCase(CatalogRepository catalogRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new CreateDocumentInCatalogUseCase(catalogId, document, catalogRepository, threadExecutor, postExecutionThread);
    }
}
