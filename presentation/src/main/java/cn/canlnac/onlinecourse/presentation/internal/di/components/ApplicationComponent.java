package cn.canlnac.onlinecourse.presentation.internal.di.components;

import android.content.Context;

import javax.inject.Singleton;

import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.domain.repository.DocumentRepository;
import cn.canlnac.onlinecourse.domain.repository.UploadRepository;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ApplicationModule;
import cn.canlnac.onlinecourse.presentation.ui.activity.BaseActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.BaseFragmentActivity;
import cn.canlnac.onlinecourse.presentation.ui.fragment.BaseFragment;
import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
    void inject(BaseFragmentActivity baseFragmentActivity);
    void inject(BaseFragment baseFragment);

    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    UserRepository userRepository();
    DocumentRepository documentRepository();
    CourseRepository courseRepository();
    CommentRepository commentRepository();
    ChatRepository chatRepository();
    CatalogRepository catalogRepository();
    UploadRepository uploadRepository();
}
