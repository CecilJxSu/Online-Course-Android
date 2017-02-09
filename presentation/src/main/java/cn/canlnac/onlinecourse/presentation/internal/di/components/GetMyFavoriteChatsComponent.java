package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMyFavoriteChatsModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyFavoriteActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetMyFavoriteChatsModule.class})
public interface GetMyFavoriteChatsComponent extends ActivityComponent {
    void inject(MyFavoriteActivity myFavoriteActivity);
}
