package cn.canlnac.onlinecourse.presentation.internal.di.components;

import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetQuestionModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.QuestionActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, GetQuestionModule.class})
public interface GetQuestionComponent extends ActivityComponent {
    void inject(QuestionActivity questionActivity);
}
