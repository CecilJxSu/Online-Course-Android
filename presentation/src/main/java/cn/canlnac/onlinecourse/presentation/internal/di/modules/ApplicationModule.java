package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.executor.JobExecutor;
import cn.canlnac.onlinecourse.data.repository.RegisterDataRepository;
import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.interactor.RegisterUseCase;
import cn.canlnac.onlinecourse.domain.interactor.UseCase;
import cn.canlnac.onlinecourse.domain.repository.RegisterRepository;
import cn.canlnac.onlinecourse.presentation.AndroidApplication;
import cn.canlnac.onlinecourse.presentation.UIThread;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */

@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    RegisterRepository provideRegisterRepository(RegisterDataRepository registerDataRepository) {
        return registerDataRepository;
    }
}
