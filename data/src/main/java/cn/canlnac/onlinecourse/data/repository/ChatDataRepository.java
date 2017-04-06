package cn.canlnac.onlinecourse.data.repository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.ChatEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.ChatListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.CommentListEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.ChatDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.ChatDataStoreFactory;
import cn.canlnac.onlinecourse.domain.Chat;
import cn.canlnac.onlinecourse.domain.ChatList;
import cn.canlnac.onlinecourse.domain.CommentList;
import cn.canlnac.onlinecourse.domain.repository.ChatRepository;
import rx.Observable;

/**
 * 话题数据接口，提供给domain调用.
 */
@Singleton
public class ChatDataRepository implements ChatRepository {
    private final ChatDataStore chatDataStore;

    private final ChatEntityDataMapper chatEntityDataMapper;
    private final CommentListEntityDataMapper commentListEntityDataMapper;
    private final ChatListEntityDataMapper chatListEntityDataMapper;

    @Inject
    public ChatDataRepository(
            ChatDataStoreFactory chatDataStoreFactory,
            ChatEntityDataMapper chatEntityDataMapper,
            CommentListEntityDataMapper commentListEntityDataMapper,
            ChatListEntityDataMapper chatListEntityDataMapper
    ) {
        this.chatDataStore = chatDataStoreFactory.create();
        this.chatEntityDataMapper = chatEntityDataMapper;
        this.commentListEntityDataMapper = commentListEntityDataMapper;
        this.chatListEntityDataMapper = chatListEntityDataMapper;
    }

    @Override
    public Observable<Chat> getChat(int chatId) {
        return chatDataStore.getChat(chatId).map(chatEntityDataMapper::transform);
    }

    @Override
    public Observable<Void> deleteChat(int chatId) {
        return chatDataStore.deleteChat(chatId);
    }

    @Override
    public Observable<Integer> createChat(Map<String, Object> chat) {
        return chatDataStore.createChat(chat);
    }

    @Override
    public Observable<Void> likeChat(int chatId) {
        return chatDataStore.likeChat(chatId);
    }

    @Override
    public Observable<Void> unlikeChat(int chatId) {
        return chatDataStore.unlikeChat(chatId);
    }

    @Override
    public Observable<Void> favoriteChat(int chatId) {
        return chatDataStore.favoriteChat(chatId);
    }

    @Override
    public Observable<Void> unfavoriteChat(int chatId) {
        return chatDataStore.unfavoriteChat(chatId);
    }

    @Override
    public Observable<CommentList> getCommentsInChat(int chatId, Integer start, Integer count, String sort) {
        return chatDataStore.getCommentsInChat(chatId,start,count,sort).map(commentListEntityDataMapper::transform);
    }

    @Override
    public Observable<Integer> createCommentInChat(int chatId, Map<String, Object> comment) {
        return chatDataStore.createCommentInChat(chatId, comment);
    }

    @Override
    public Observable<Integer> replyCommentInChat(int chatId, int commentId, Map<String, Object> reply) {
        return chatDataStore.replyCommentInChat(chatId, commentId, reply);
    }

    @Override
    public Observable<ChatList> getChats(Integer start, Integer count, String sort) {
        return chatDataStore.getChats(start, count, sort).map(chatListEntityDataMapper::transform);
    }
}
