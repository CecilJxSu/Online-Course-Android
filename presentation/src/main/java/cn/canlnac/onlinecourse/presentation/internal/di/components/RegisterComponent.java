package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.RegisterModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;
import dagger.Component;

/**
 * 注册页面注入.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, RegisterModule.class})
public interface RegisterComponent extends ActivityComponent {
    void inject(RegisterActivity registerActivity);
}
