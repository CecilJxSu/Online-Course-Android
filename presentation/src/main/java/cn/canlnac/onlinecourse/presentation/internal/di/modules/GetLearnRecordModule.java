package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.GetLearnRecordUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class GetLearnRecordModule {

    private final int catalogId;

    public GetLearnRecordModule(
            int catalogId
    ) {
        this.catalogId = catalogId;
    }

    @Provides
    @PerActivity
    UseCase provideGetLearnRecordUseCase(CatalogRepository catalogRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new GetLearnRecordUseCase(catalogId, catalogRepository, threadExecutor, postExecutionThread);
    }
}
