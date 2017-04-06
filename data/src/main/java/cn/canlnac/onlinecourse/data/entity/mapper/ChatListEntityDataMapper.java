package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.ChatListEntity;
import cn.canlnac.onlinecourse.domain.ChatList;

/**
 * 话题列表实体类转换.
 */
@Singleton
public class ChatListEntityDataMapper {
    private ChatEntityDataMapper chatEntityDataMapper;

    @Inject
    public ChatListEntityDataMapper(ChatEntityDataMapper chatEntityDataMapper) {
        this.chatEntityDataMapper = chatEntityDataMapper;
    }

    /**
     * 转换
     * @param chatListEntity 话题列表实体类
     * @return
     */
    public ChatList transform(ChatListEntity chatListEntity) {
        ChatList chatList = null;
        if (chatListEntity != null) {
            chatList = new ChatList();
            chatList.setTotal(chatListEntity.getTotal());
            chatList.setChats(chatEntityDataMapper.transform(chatListEntity.getChats()));
        }
        return chatList;
    }
}
