package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMessageModule;
import cn.canlnac.onlinecourse.presentation.ui.adapter.MessageViewHolder;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetMessageModule.class})
public interface GetMessageComponent extends ActivityComponent {
    void inject(MessageViewHolder messageViewHolder);
}
