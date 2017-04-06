package cn.canlnac.onlinecourse.domain.interactor;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UploadRepository;
import rx.Observable;

/**
 * 上传文件使用用例.
 */

public class UploadUseCase extends UseCase {

    private final List<File> files;

    private final UploadRepository uploadRepository;

    @Inject
    public UploadUseCase(
            List<File> files,
            UploadRepository uploadRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.files = files;
        this.uploadRepository = uploadRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.uploadRepository.uploadFiles(files);
    }
}
