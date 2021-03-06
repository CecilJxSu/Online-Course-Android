package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.CreateCommentInCourseModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInCourseFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CreateCommentInCourseModule.class})
public interface CreateCommentInCourseComponent extends ActivityComponent {
    void inject(PostCommentInCourseFragment postCommentInCourseFragment);
}
