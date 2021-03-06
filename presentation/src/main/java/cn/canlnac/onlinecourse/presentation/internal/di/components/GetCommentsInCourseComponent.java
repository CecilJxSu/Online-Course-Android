package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCommentsInCourseModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseCommentFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetCommentsInCourseModule.class})
public interface GetCommentsInCourseComponent extends ActivityComponent {
    void inject(CourseCommentFragment courseCommentFragment);
}
