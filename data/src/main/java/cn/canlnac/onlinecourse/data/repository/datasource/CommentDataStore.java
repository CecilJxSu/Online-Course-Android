package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.entity.CommentEntity;
import rx.Observable;

/**
 * 评论数据储存.
 */

public interface CommentDataStore {
    /** 点赞评论 */
    Observable<Void> likeComment(int commentId);
    /** 取消点赞评论 */
    Observable<Void> unlikeComment(int commentId);
    /** 获取指定评论 */
    Observable<CommentEntity> getComment(int commentId);
}
