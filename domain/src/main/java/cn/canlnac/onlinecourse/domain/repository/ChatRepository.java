package cn.canlnac.onlinecourse.domain.repository;

import com.sun.istack.internal.Nullable;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.Chat;
import cn.canlnac.onlinecourse.domain.ChatList;
import cn.canlnac.onlinecourse.domain.CommentList;
import rx.Observable;

/**
 * 话题接口.
 */
public interface ChatRepository {
    /** 获取话题 */
    Observable<Chat> getChat(int chatId);
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
    Observable<CommentList> getCommentsInChat(int chatId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort);
    /** 评论话题 */
    Observable<Integer> createCommentInChat(int chatId, Map<String,Object> comment);
    /** 回复评论 */
    Observable<Integer> replyCommentInChat(int chatId, int commentId, Map<String,Object> reply);

    /** 话题列表 */
    Observable<ChatList> getChats(@Nullable Integer start, @Nullable Integer count, @Nullable String sort);
}
