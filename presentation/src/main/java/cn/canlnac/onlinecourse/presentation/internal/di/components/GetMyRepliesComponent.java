package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMyRepliesModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyRepliesActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetMyRepliesModule.class})
public interface GetMyRepliesComponent extends ActivityComponent {
    void inject(MyRepliesActivity myRepliesActivity);
}
