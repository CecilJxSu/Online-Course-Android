package cn.canlnac.onlinecourse.data.repository.datasource;

import rx.Observable;

/**
 * 评论数据储存.
 */

public interface CommentDataStore {
    /** 点赞评论 */
    Observable<Void> likeComment(int commentId);
    /** 取消点赞评论 */
    Observable<Void> unlikeComment(int commentId);
}
