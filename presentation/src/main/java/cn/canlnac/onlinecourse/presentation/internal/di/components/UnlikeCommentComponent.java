package cn.canlnac.onlinecourse.presentation.internal.di.components;


import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UnlikeCommentModule;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CommentInChatViewHolder;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CommentViewHolder;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UnlikeCommentModule.class})
public interface UnlikeCommentComponent extends ActivityComponent {
    void inject(CommentViewHolder commentViewHolder);
    void inject(CommentInChatViewHolder commentInChatViewHolder);
}
