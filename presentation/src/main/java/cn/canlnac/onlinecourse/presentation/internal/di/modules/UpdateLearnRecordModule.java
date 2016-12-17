package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import rx.Observable;

/**
 * 更新学习记录使用用例.
 */

public class UpdateLearnRecordModule extends UseCase {

    private final int catalogId;
    private final Map<String,Object> learnRecord;

    private final CatalogRepository catalogRepository;

    @Inject
    public UpdateLearnRecordModule(
            int catalogId,
            Map<String,Object> learnRecord,
            CatalogRepository catalogRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.catalogId = catalogId;
        this.learnRecord = learnRecord;
        this.catalogRepository = catalogRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.catalogRepository.updateLearnRecord(catalogId, learnRecord);
    }
}
