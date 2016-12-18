package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.MessageList;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.MessageListModel;

@PerActivity
public class MessageListModelDataMapper {
    private final MessageModelDataMapper messageModelDataMapper;
    @Inject
    public MessageListModelDataMapper(MessageModelDataMapper messageModelDataMapper) {
        this.messageModelDataMapper = messageModelDataMapper;
    }

    public MessageListModel transform(MessageList messageList) {
        if (messageList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        MessageListModel messageListModel = new MessageListModel();
        messageListModel.setTotal(messageList.getTotal());
        messageListModel.setMessages(messageModelDataMapper.transform(messageList.getMessages()));

        return messageListModel;
    }
}
