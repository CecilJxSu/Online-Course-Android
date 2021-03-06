package cn.canlnac.onlinecourse.domain.interactor;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import rx.Observable;

/**
 * 获取问题使用用例.
 */

public class GetQuestionUseCase extends UseCase {

    private final int catalogId;

    private final CatalogRepository catalogRepository;

    @Inject
    public GetQuestionUseCase(
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
        return this.catalogRepository.getQuestion(catalogId);
    }
}
