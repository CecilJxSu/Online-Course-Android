package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCommentModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.PostCommentInCourseFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetCommentModule.class})
public interface GetCommentInCourseComponent extends ActivityComponent {
    void inject(PostCommentInCourseFragment postCommentInCourseFragment);
}
