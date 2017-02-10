package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.MessageEntity;
import cn.canlnac.onlinecourse.domain.Message;

/**
 * 用户消息实体类转换.
 */
@Singleton
public class MessageEntityDataMapper {
    private SimpleUserEntityDataMapper simpleUserEntityDataMapper;
    private PositionEntityDataMapper positionEntityDataMapper;

    @Inject
    public MessageEntityDataMapper(SimpleUserEntityDataMapper simpleUserEntityDataMapper, PositionEntityDataMapper positionEntityDataMapper) {
        this.simpleUserEntityDataMapper = simpleUserEntityDataMapper;
        this.positionEntityDataMapper = positionEntityDataMapper;
    }

    /**
     * 转换
     * @param messageEntity 用户消息实体类
     * @return
     */
    public Message transform(MessageEntity messageEntity) {
        Message message = null;
        if (messageEntity != null) {
            message = new Message();
            message.setId(messageEntity.getId());
            message.setActionType(messageEntity.getActionType());
            message.setContent(messageEntity.getContent());
            message.setDate(messageEntity.getDate());
            message.setFromUser(simpleUserEntityDataMapper.transform(messageEntity.getFromUser()));
            message.setToUser(simpleUserEntityDataMapper.transform(messageEntity.getToUser()));
            message.setPosition(positionEntityDataMapper.transform(messageEntity.getPosition()));
            message.setType(messageEntity.getType());
        }
        return message;
    }

    /**
     * 转换列表
     * @param messageEntityList    用户消息实体类列表
     * @return
     */
    public List<Message> transform(List<MessageEntity> messageEntityList) {
        List<Message> messageList = new ArrayList<>(messageEntityList.size());
        Message message;
        for (MessageEntity messageEntity : messageEntityList) {
            message = transform(messageEntity);
            if (message != null) {
                messageList.add(message);
            }
        }

        return messageList;
    }
}
