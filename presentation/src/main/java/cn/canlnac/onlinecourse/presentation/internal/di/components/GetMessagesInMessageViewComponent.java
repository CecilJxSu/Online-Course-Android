package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMessagesInMessageViewModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.MessageActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetMessagesInMessageViewModule.class})
public interface GetMessagesInMessageViewComponent extends ActivityComponent {
    void inject(MessageActivity messageActivity);
}
