package cn.canlnac.onlinecourse.domain.interactor;

import java.util.Map;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 创建文档使用用例.
 */

public class CreateDocumentUseCase extends UseCase {

    private final int courseId;
    private final Map<String, String> document;

    private final CourseRepository courseRepository;

    @Inject
    public CreateDocumentUseCase(
            int courseId,
            Map<String, String> document,
            CourseRepository courseRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.courseId = courseId;
        this.document = document;
        this.courseRepository = courseRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.courseRepository.createDocument(courseId, document);
    }
}
