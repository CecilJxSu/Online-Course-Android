package cn.canlnac.onlinecourse.data.repository.datasource;

import android.support.annotation.Nullable;

import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.ChatEntity;
import cn.canlnac.onlinecourse.data.entity.ChatListEntity;
import cn.canlnac.onlinecourse.data.entity.CommentListEntity;
import rx.Observable;

/**
 * 话题数据储存.
 */

public interface ChatDataStore {
    /** 获取话题 */
    Observable<ChatEntity> getChat(int chatId);
    /** 删除话题 */
    Observable<Void> deleteChat(int chatId);
    /** 创建话题 */
    Observable<Integer> createChat(Map<String,Object> chat);
    /** 点赞话题 */
    Observable<Void> likeChat(int chatId);
    /** 取消点赞话题 */
    Observable<Void> unlikeChat(int chatId);
    /** 收藏话题 */
    Observable<Void> favoriteChat(int chatId);
    /** 取消收藏话题 */
    Observable<Void> unfavoriteChat(int chatId);
    /** 获取话题下的评论 */
    Observable<CommentListEntity> getCommentsInChat(int chatId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort);
    /** 评论话题 */
    Observable<Integer> createCommentInChat(int chatId, Map<String,Object> comment);
    /** 回复评论 */
    Observable<Integer> replyCommentInChat(int chatId, int commentId, Map<String,Object> reply);

    /** 话题列表 */
    Observable<ChatListEntity> getChats(@Nullable Integer start, @Nullable Integer count, @Nullable String sort);
}
