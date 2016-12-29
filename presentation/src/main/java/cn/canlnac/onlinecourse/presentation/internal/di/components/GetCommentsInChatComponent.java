package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCommentsInChatModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.CommentActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetCommentsInChatModule.class})
public interface GetCommentsInChatComponent extends ActivityComponent {
    void inject(CommentActivity commentActivity);
}
