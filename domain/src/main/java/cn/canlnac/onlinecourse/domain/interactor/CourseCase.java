package cn.canlnac.onlinecourse.domain.interactor;


import cn.canlnac.onlinecourse.domain.executor.PostExecutionThread;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * 课程用例，创建UI线程，使用 {@link rx.rx.Subscriber} 执行.
 */

public abstract class CourseCase {
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Subscription subscription = Subscriptions.empty();

    protected CourseCase(
            ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread)
    {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    protected abstract Observable buildCourseCaseObservable();

    public void execute(Subscriber courseCaseSubscriber) {
        
    }
}
