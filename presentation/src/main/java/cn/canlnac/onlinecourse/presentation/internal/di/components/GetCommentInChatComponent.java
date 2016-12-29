package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCommentModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInChatFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetCommentModule.class})
public interface GetCommentInChatComponent extends ActivityComponent {
    void inject(PostCommentInChatFragment postCommentInChatFragment);
}
