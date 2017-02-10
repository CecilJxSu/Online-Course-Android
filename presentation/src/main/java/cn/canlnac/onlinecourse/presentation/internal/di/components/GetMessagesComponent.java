package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMessagesModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.MainActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetMessagesModule.class})
public interface GetMessagesComponent extends ActivityComponent {
    void inject(MainActivity mainActivity);
}
