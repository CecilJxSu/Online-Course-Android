package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetChatsChatModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment2;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetChatsChatModule.class})
public interface GetChatsChatComponent extends ActivityComponent {
    void inject(TabFragment2 tabFragment2);
}
