package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetUserProfileModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.ChatActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetUserProfileModule.class})
public interface GetUserProfileInChatComponent extends ActivityComponent {
    void inject(ChatActivity chatActivity);
}
