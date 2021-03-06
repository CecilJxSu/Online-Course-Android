package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.LikeChatModule;
import cn.canlnac.onlinecourse.presentation.ui.adapter.ChatViewHolder;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LikeChatModule.class})
public interface LikeChatComponent extends ActivityComponent {
    void inject(ChatViewHolder registerActivity);
}
