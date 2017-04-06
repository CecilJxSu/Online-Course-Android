package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.CourseIntroModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.CourseIntroFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CourseIntroModule.class})
public interface CourseIntroComponent extends ActivityComponent {
    void inject(CourseIntroFragment courseIntroFragment);
}
