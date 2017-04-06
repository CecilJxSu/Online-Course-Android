package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UpdateUserProfileModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.ProfileActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UpdateUserProfileModule.class})
public interface UpdateUserProfileComponent extends ActivityComponent {
    void inject(ProfileActivity profileActivity);
}
