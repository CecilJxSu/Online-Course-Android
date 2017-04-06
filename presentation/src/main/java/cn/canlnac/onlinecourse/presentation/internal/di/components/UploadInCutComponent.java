package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UploadModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.CutActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UploadModule.class})
public interface UploadInCutComponent extends ActivityComponent {
    void inject(CutActivity cutActivity);
}
