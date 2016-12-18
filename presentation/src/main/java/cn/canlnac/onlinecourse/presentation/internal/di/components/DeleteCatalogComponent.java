package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.DeleteCatalogModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, DeleteCatalogModule.class})
public interface DeleteCatalogComponent extends ActivityComponent {
    void inject(RegisterActivity registerActivity);
}
