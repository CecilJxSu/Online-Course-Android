package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UnlikeChatModule;
import cn.canlnac.onlinecourse.presentation.ui.adapter.ChatViewHolder;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UnlikeChatModule.class})
public interface UnlikeChatComponent extends ActivityComponent {
    void inject(ChatViewHolder chatViewHolder);
}
