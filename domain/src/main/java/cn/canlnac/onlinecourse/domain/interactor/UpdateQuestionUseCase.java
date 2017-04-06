package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import rx.Observable;

/**
 * 更新问题使用用例.
 */

public class UpdateQuestionUseCase extends UseCase {

    private final int catalogId;
    private final Map<String,Object> question;

    private final CatalogRepository catalogRepository;

    @Inject
    public UpdateQuestionUseCase(
            int catalogId,
            Map<String,Object> question,
            CatalogRepository catalogRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.catalogId = catalogId;
        this.question = question;
        this.catalogRepository = catalogRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.catalogRepository.updateQuestion(catalogId,question);
    }
}
