package cn.canlnac.onlinecourse.presentation.internal.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.executor.JobExecutor;
import cn.canlnac.onlinecourse.data.repository.CatalogDataRepository;
import cn.canlnac.onlinecourse.data.repository.ChatDataRepository;
import cn.canlnac.onlinecourse.data.repository.CommentDataRepository;
import cn.canlnac.onlinecourse.data.repository.CourseDataRepository;
import cn.canlnac.onlinecourse.data.repository.DocumentDataRepository;
import cn.canlnac.onlinecourse.data.repository.UserDataRepository;
import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import cn.canlnac.onlinecourse.domain.repository.DocumentRepository;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import cn.canlnac.onlinecourse.presentation.AndroidApplication;
import cn.canlnac.onlinecourse.presentation.UIThread;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */

@Module
public class ApplicationModule {
    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }

    @Provides
    @Singleton
    DocumentRepository provideDocumentRepository(DocumentDataRepository documentDataRepository) {
        return documentDataRepository;
    }

    @Provides
    @Singleton
    CourseRepository provideCourseRepository(CourseDataRepository courseDataRepository) {
        return courseDataRepository;
    }

    @Provides
    @Singleton
    CommentRepository provideCommentRepository(CommentDataRepository commentDataRepository) {
        return commentDataRepository;
    }

    @Provides
    @Singleton
    ChatRepository provideChatRepository(ChatDataRepository chatDataRepository) {
        return chatDataRepository;
    }

    @Provides
    @Singleton
    CatalogRepository provideCatalogRepository(CatalogDataRepository catalogDataRepository) {
        return catalogDataRepository;
    }
}
