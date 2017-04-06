package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCourseModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetCourseModule.class})
public interface GetCourseComponent extends ActivityComponent {
    void inject(CourseActivity courseActivity);
}
