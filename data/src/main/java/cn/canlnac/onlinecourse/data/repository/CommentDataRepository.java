package cn.canlnac.onlinecourse.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.repository.datasource.CommentDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.CommentDataStoreFactory;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import rx.Observable;

/**
 * 评论数据接口，提供给domain调用.
 */
@Singleton
public class CommentDataRepository implements CommentRepository {
    private final CommentDataStore commentDataStore;

    @Inject
    public CommentDataRepository(
            CommentDataStoreFactory commentDataStoreFactory
    ) {
        this.commentDataStore = commentDataStoreFactory.create();
    }

    @Override
    public Observable<Void> likeComment(int commentId) {
        return commentDataStore.likeComment(commentId);
    }

    @Override
    public Observable<Void> unlikeComment(int commentId) {
        return commentDataStore.unlikeComment(commentId);
    }
}
