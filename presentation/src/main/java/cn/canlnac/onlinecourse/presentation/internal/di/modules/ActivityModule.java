package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import android.app.Activity;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }
}
