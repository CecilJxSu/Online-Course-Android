package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 创建目录使用用例.
 */

public class CreateCatalogUseCase extends UseCase {

    private final int courseId;
    private final Map<String, Object> catalog;

    private final CourseRepository courseRepository;

    @Inject
    public CreateCatalogUseCase(
            int courseId,
            Map<String, Object> catalog,
            CourseRepository courseRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.courseId = courseId;
        this.catalog = catalog;
        this.courseRepository = courseRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.courseRepository.createCatalog(courseId, catalog);
    }
}
