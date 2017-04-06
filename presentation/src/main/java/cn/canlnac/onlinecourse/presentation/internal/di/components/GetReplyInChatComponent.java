package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetReplyModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostReplyInChatFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetReplyModule.class})
public interface GetReplyInChatComponent extends ActivityComponent {
    void inject(PostReplyInChatFragment postReplyInChatFragment);
}
