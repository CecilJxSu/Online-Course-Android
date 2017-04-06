package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.DeleteMessageModule;
import cn.canlnac.onlinecourse.presentation.ui.adapter.MessageViewHolder;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, DeleteMessageModule.class})
public interface DeleteMessageComponent extends ActivityComponent {
    void inject(MessageViewHolder messageViewHolder);
}
