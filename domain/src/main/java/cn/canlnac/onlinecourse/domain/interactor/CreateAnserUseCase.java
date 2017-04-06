package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import rx.Observable;

/**
 * 创建问题使用用例.
 */

public class CreateAnserUseCase extends UseCase {

    private final int catalogId;
    private final Map<String, Object> answer;

    private final CatalogRepository catalogRepository;

    @Inject
    public CreateAnserUseCase(
            int catalogId,
            Map<String, Object> answer,
            CatalogRepository catalogRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.catalogId = catalogId;
        this.answer = answer;
        this.catalogRepository = catalogRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.catalogRepository.createAnser(catalogId, answer);
    }
}
