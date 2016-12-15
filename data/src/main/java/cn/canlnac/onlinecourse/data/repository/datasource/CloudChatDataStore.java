package cn.canlnac.onlinecourse.data.repository.datasource;

import android.support.annotation.Nullable;

import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.ChatEntity;
import cn.canlnac.onlinecourse.data.entity.ChatListEntity;
import cn.canlnac.onlinecourse.data.entity.CommentListEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;
import rx.Observable;

/**
 * 从服务器中获取话题数据.
 */

public class CloudChatDataStore implements ChatDataStore {
    private final RestApi restApi;

    public CloudChatDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<ChatEntity> getChat(int chatId) {
        return this.restApi.getChat(chatId);
    }

    @Override
    public Observable<Void> deleteChat(int chatId) {
        return this.restApi.deleteChat(chatId);
    }

    @Override
    public Observable<Integer> createChat(Map<String, Object> chat) {
        return this.restApi.createChat(chat);
    }

    @Override
    public Observable<Void> likeChat(int chatId) {
        return this.restApi.likeChat(chatId);
    }

    @Override
    public Observable<Void> unlikeChat(int chatId) {
        return this.restApi.unlikeChat(chatId);
    }

    @Override
    public Observable<Void> favoriteChat(int chatId) {
        return this.restApi.favoriteChat(chatId);
    }

    @Override
    public Observable<Void> unfavoriteChat(int chatId) {
        return this.restApi.unfavoriteChat(chatId);
    }

    @Override
    public Observable<CommentListEntity> getCommentsInChat(int chatId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return this.restApi.getCommentsInChat(chatId, start, count, sort);
    }

    @Override
    public Observable<Integer> createCommentInChat(int chatId, Map<String, Object> comment) {
        return this.restApi.createCommentInChat(chatId, comment);
    }

    @Override
    public Observable<Integer> replyCommentInChat(int chatId, int commentId, Map<String, Object> reply) {
        return this.restApi.replyCommentInChat(chatId, commentId, reply);
    }

    @Override
    public Observable<ChatListEntity> getChats(@Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return this.restApi.getChats(start, count, sort);
    }
}
