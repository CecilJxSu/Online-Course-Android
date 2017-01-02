package cn.canlnac.onlinecourse.domain.interactor;

import java.io.File;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.UploadRepository;
import rx.Observable;

/**
 * 下载文件使用用例.
 */

public class DownloadUseCase extends UseCase {

    private final String fileUrl;
    private final File targetFile;

    private final UploadRepository uploadRepository;

    @Inject
    public DownloadUseCase(
            String fileUrl,
            File targetFile,
            UploadRepository uploadRepository,
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread
    ) {
        super(threadExecutor, postExecutionThread);

        this.fileUrl = fileUrl;
        this.targetFile = targetFile;

        this.uploadRepository = uploadRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return this.uploadRepository.download(fileUrl, targetFile);
    }
}
