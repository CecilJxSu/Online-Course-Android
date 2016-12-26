package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetChatModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.ChatActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetChatModule.class})
public interface GetChatComponent extends ActivityComponent {
    void inject(ChatActivity chatActivity);
}
