package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetDocumentsInCourseModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetDocumentsInCourseModule.class})
public interface GetDocumentsInCourseComponent extends ActivityComponent {
    void inject(RegisterActivity registerActivity);
}
