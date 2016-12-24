package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.entity.CommentEntity;
import cn.canlnac.onlinecourse.data.entity.ReplyEntity;
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
    /** 获取指定回复 */
    Observable<ReplyEntity> getReply(int replyId);
}
