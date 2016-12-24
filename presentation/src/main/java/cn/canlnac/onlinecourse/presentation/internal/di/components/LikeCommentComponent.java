package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.LikeCommentModule;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CommentViewHolder;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, LikeCommentModule.class})
public interface LikeCommentComponent extends ActivityComponent {
    void inject(CommentViewHolder commentViewHolder);
}
