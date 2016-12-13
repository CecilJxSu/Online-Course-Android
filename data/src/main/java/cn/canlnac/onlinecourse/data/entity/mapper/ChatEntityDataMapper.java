package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.ChatEntity;
import cn.canlnac.onlinecourse.domain.Chat;

/**
 * 话题实体类转换.
 */
@Singleton
public class ChatEntityDataMapper {
    private LoginEntityDataMapper loginEntityDataMapper;

    @Inject
    public ChatEntityDataMapper(LoginEntityDataMapper loginEntityDataMapper) {
        this.loginEntityDataMapper = loginEntityDataMapper;
    }

    /**
     * 转换
     * @param chatEntity 话题实体类
     * @return
     */
    public Chat transform(ChatEntity chatEntity) {
        Chat chat = null;
        if (chatEntity != null) {
            chat = new Chat();
            chat.setDate(chatEntity.getDate());
            chat.setAuthor(loginEntityDataMapper.transform(chatEntity.getAuthor()));
            chat.setCommentCount(chatEntity.getCommentCount());
            chat.setContent(chatEntity.getContent());
            chat.setFavorite(chatEntity.isFavorite());
            chat.setId(chatEntity.getId());
            chat.setLike(chatEntity.isLike());
            chat.setLikeCount(chatEntity.getLikeCount());
            chat.setPictureUrls(chatEntity.getPictureUrls());
            chat.setTitle(chatEntity.getTitle());
            chat.setWatchCount(chatEntity.getWatchCount());
            chat.setFavoriteCount(chatEntity.getFavoriteCount());
        }
        return chat;
    }

    /**
     * 转换列表
     * @param chatEntityList    话题实体类列表
     * @return
     */
    public List<Chat> transform(List<ChatEntity> chatEntityList) {
        List<Chat> chatList = new ArrayList<>(chatEntityList.size());
        Chat chat;
        for (ChatEntity chatEntity : chatEntityList) {
            chat = transform(chatEntity);
            if (chat != null) {
                chatList.add(chat);
            }
        }

        return chatList;
    }
}
