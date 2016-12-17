package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.UpdateLearnRecordUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class UpdateLearnRecordModule {

    private final int catalogId;
    private final Map<String,Object> learnRecord;

    public UpdateLearnRecordModule(
            int catalogId,
            Map<String,Object> learnRecord
    ) {
        this.catalogId = catalogId;
        this.learnRecord = learnRecord;
    }

    @Provides
    @PerActivity
    UseCase provideUpdateLearnRecordUseCase(CatalogRepository catalogRepository, ThreadExecutor threadExecutor,
                                            PostExecutionThread postExecutionThread){
        return new UpdateLearnRecordUseCase(catalogId,learnRecord, catalogRepository, threadExecutor, postExecutionThread);
    }
}
