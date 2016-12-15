package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.MessageListEntity;
import cn.canlnac.onlinecourse.domain.MessageList;

/**
 * 消息列表实体类转换.
 */
@Singleton
public class MessageListEntityDataMapper {
    private MessageEntityDataMapper messageEntityDataMapper;

    @Inject
    public MessageListEntityDataMapper(MessageEntityDataMapper messageEntityDataMapper) {
        this.messageEntityDataMapper = messageEntityDataMapper;
    }

    /**
     * 转换
     * @param messageListEntity 消息列表实体类
     * @return
     */
    public MessageList transform(MessageListEntity messageListEntity) {
        MessageList messageList = null;
        if (messageListEntity != null) {
            messageList = new MessageList();
            messageList.setTotal(messageListEntity.getTotal());
            messageList.setMessages(messageEntityDataMapper.transform(messageListEntity.getMessages()));
        }
        return messageList;
    }
}
