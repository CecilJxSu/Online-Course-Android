package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import java.io.File;
import java.util.List;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.UploadUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.UploadRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 注入模块.
 */
@Module
public class UploadModule {

    private final List<File> files;

    public UploadModule(
            List<File> files
    ) {
        this.files = files;
    }

    @Provides
    @PerActivity
    UseCase provideUploadUseCase(UploadRepository uploadRepository, ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread){
        return new UploadUseCase(files, uploadRepository, threadExecutor, postExecutionThread);
    }
}
