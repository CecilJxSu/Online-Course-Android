package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.DownloadModule;
import cn.canlnac.onlinecourse.presentation.ui.fragment.DocumentFragment;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, DownloadModule.class})
public interface DownloadInDocumentComponent extends ActivityComponent {
    void inject(DocumentFragment documentFragment);
}
