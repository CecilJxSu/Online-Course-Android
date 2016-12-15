package cn.canlnac.onlinecourse.domain.repository;

import rx.Observable;

/**
 * 评论接口.
 */
public interface CommentRepository {
    /** 点赞评论 */
    Observable<Void> likeComment(int commentId);
    /** 取消点赞评论 */
    Observable<Void> unlikeComment(int commentId);
}
