package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMyFavoriteCoursesModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyFavoriteActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetMyFavoriteCoursesModule.class})
public interface GetMyFavoriteCoursesComponent extends ActivityComponent {
    void inject(MyFavoriteActivity myFavoriteActivity);
}
