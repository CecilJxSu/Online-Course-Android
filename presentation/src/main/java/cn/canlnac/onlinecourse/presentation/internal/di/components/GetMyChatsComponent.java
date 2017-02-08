package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMyChatsModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyChatActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetMyChatsModule.class})
public interface GetMyChatsComponent extends ActivityComponent {
    void inject(MyChatActivity myChatActivity);
}
