package cn.canlnac.onlinecourse.presentation;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import cn.canlnac.onlinecourse.presentation.internal.di.components.ApplicationComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerApplicationComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ApplicationModule;

/**
 * Android Main Application.
 */

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }
}
