package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.io.File;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.DownloadUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UploadRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class DownloadModule {

    private final String fileUrl;
    private final File targetFile;

    public DownloadModule(
            String fileUrl,
            File targetFile
    ) {
        this.fileUrl = fileUrl;
        this.targetFile = targetFile;
    }

    @Provides
    @PerActivity
    UseCase provideDownloadUseCase(UploadRepository uploadRepository, ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread){
        return new DownloadUseCase(fileUrl, targetFile, uploadRepository, threadExecutor, postExecutionThread);
    }
}
