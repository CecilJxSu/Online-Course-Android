package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.CreateCommentInChatModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInChatFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CreateCommentInChatModule.class})
public interface CreateCommentInChatComponent extends ActivityComponent {
    void inject(PostCommentInChatFragment postCommentInChatFragment);
}
