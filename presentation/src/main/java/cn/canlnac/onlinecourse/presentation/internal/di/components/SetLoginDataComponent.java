package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.SetLoginDataModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment3;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, SetLoginDataModule.class})
public interface SetLoginDataComponent extends ActivityComponent {
    void inject(TabFragment3 tabFragment3);
}
