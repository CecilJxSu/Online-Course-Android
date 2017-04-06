package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.CreateQuestionUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class CreateQuestionModule {

    private final int catalogId;
    private final Map<String,Object> question;

    public CreateQuestionModule(
            int catalogId,
            Map<String,Object> question
    ) {
        this.catalogId = catalogId;
        this.question = question;
    }

    @Provides
    @PerActivity
    UseCase provideCreateQuestionUseCase(CatalogRepository catalogRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new CreateQuestionUseCase(catalogId, question, catalogRepository, threadExecutor, postExecutionThread);
    }
}
