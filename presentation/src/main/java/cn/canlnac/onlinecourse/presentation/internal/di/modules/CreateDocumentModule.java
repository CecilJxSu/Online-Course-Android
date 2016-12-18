package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.CreateDocumentUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class CreateDocumentModule {

    private final int courseId;
    private final Map<String, String> document;

    public CreateDocumentModule(
            int courseId,
            Map<String, String> document
    ) {
        this.courseId = courseId;
        this.document = document;
    }

    @Provides
    @PerActivity
    UseCase provideCreateDocumentUseCase(CourseRepository courseRepository, ThreadExecutor threadExecutor,
                                   PostExecutionThread postExecutionThread){
        return new CreateDocumentUseCase(courseId, document, courseRepository, threadExecutor, postExecutionThread);
    }
}
