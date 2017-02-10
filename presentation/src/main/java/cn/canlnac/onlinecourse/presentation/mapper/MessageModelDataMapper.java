package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Message;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.MessageModel;

@PerActivity
public class MessageModelDataMapper {
    private final SimpleUserModelDataMapper simpleUserModelDataMapper;
    private final PositionModelDataMapper positionModelDataMapper;

    @Inject
    public MessageModelDataMapper(SimpleUserModelDataMapper simpleUserModelDataMapper,PositionModelDataMapper positionModelDataMapper) {
        this.simpleUserModelDataMapper = simpleUserModelDataMapper;
        this.positionModelDataMapper = positionModelDataMapper;
    }

    public MessageModel transform(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        MessageModel messageModel = new MessageModel();
        messageModel.setId(message.getId());
        messageModel.setDate(message.getDate());
        messageModel.setActionType(message.getActionType());
        messageModel.setContent(message.getContent());
        if (message.getFromUser() != null) {
            messageModel.setFromUser(simpleUserModelDataMapper.transform(message.getFromUser()));
        }
        if (message.getPosition() != null) {
            messageModel.setPositionModel(positionModelDataMapper.transform(message.getPosition()));
        }
        messageModel.setToUser(simpleUserModelDataMapper.transform(message.getToUser()));
        messageModel.setType(message.getType());

        return messageModel;
    }

    public List<MessageModel> transform(List<Message> messageList) {
        List<MessageModel> messageModelList = new ArrayList<>(messageList.size());
        MessageModel messageModel;
        for (Message message : messageList) {
            messageModel = transform(message);
            if (message != null) {
                messageModelList.add(messageModel);
            }
        }

        return messageModelList;
    }
}
