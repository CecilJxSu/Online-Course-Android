package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.CreateAnserUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class CreateAnswerModule {

    private final int catalogId;
    private final Map<String, Object> answer;

    public CreateAnswerModule(int catalogId, Map<String, Object> answer) {
        this.catalogId = catalogId;
        this.answer = answer;
    }

    @Provides
    @PerActivity
    UseCase provideCreateAnswerUseCase(CatalogRepository catalogRepository, ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread){
        return new CreateAnserUseCase(catalogId, answer, catalogRepository, threadExecutor, postExecutionThread);
    }
}
