package cn.canlnac.onlinecourse.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.CommentEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.ReplyEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.CommentDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.CommentDataStoreFactory;
import cn.canlnac.onlinecourse.domain.Comment;
import cn.canlnac.onlinecourse.domain.Reply;
import cn.canlnac.onlinecourse.domain.repository.CommentRepository;
import rx.Observable;

/**
 * 评论数据接口，提供给domain调用.
 */
@Singleton
public class CommentDataRepository implements CommentRepository {
    private final CommentDataStore commentDataStore;
    private final CommentEntityDataMapper commentEntityDataMapper;
    private final ReplyEntityDataMapper replyEntityDataMapper;

    @Inject
    public CommentDataRepository(
            CommentDataStoreFactory commentDataStoreFactory,
            CommentEntityDataMapper commentEntityDataMapper,
            ReplyEntityDataMapper replyEntityDataMapper
    ) {
        this.commentDataStore = commentDataStoreFactory.create();
        this.commentEntityDataMapper = commentEntityDataMapper;
        this.replyEntityDataMapper = replyEntityDataMapper;
    }

    @Override
    public Observable<Void> likeComment(int commentId) {
        return commentDataStore.likeComment(commentId);
    }

    @Override
    public Observable<Void> unlikeComment(int commentId) {
        return commentDataStore.unlikeComment(commentId);
    }

    @Override
    public Observable<Comment> getComment(int commentId) {
        return commentDataStore.getComment(commentId).map(commentEntityDataMapper::transform);
    }

    @Override
    public Observable<Reply> getReply(int replyId) {
        return commentDataStore.getReply(replyId).map(replyEntityDataMapper::transform);
    }
}
