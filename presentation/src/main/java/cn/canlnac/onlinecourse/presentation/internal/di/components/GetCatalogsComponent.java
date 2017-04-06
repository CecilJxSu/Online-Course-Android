package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetCatalogsModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetCatalogsModule.class})
public interface GetCatalogsComponent extends ActivityComponent {
    void inject(CourseActivity courseActivity);
}
