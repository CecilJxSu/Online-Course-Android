package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.CreateChatModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.PostChatActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CreateChatModule.class})
public interface CreateChatComponent extends ActivityComponent {
    void inject(PostChatActivity postChatActivity);
}
