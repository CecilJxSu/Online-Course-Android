package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ReplyCommentInCourseModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostReplyInCourseFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ReplyCommentInCourseModule.class})
public interface ReplyCommentInCourseComponent extends ActivityComponent {
    void inject(PostReplyInCourseFragment replyInCourseFragment);
}
