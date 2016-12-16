package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.ChatList;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.ChatListModel;

@PerActivity
public class ChatListModelDataMapper {
    private final ChatModelDataMapper chatModelDataMapper;
    @Inject
    public ChatListModelDataMapper(ChatModelDataMapper chatModelDataMapper) {
        this.chatModelDataMapper = chatModelDataMapper;
    }

    public ChatListModel transform(ChatList chatList) {
        if (chatList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ChatListModel chatListModel = new ChatListModel();
        chatListModel.setTotal(chatList.getTotal());
        chatListModel.setChats(chatModelDataMapper.transform(chatList.getChats()));

        return chatListModel;
    }
}
