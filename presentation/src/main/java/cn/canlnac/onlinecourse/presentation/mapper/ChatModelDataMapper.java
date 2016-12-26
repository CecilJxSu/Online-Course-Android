package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Chat;
import cn.canlnac.onlinecourse.domain.SimpleUser;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.ChatModel;

@PerActivity
public class ChatModelDataMapper {
    private final SimpleUserModelDataMapper simpleUserModelDataMapper;
    @Inject
    public ChatModelDataMapper(SimpleUserModelDataMapper simpleUserModelDataMapper) {
        this.simpleUserModelDataMapper = simpleUserModelDataMapper;
    }

    public ChatModel transform(Chat chat) {
        if (chat == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ChatModel chatModel = new ChatModel();
        chatModel.setId(chat.getId());
        if (chat.getAuthor() == null) {
            chat.setAuthor(new SimpleUser());
        }
        chatModel.setAuthor(simpleUserModelDataMapper.transform(chat.getAuthor()));
        chatModel.setCommentCount(chat.getCommentCount());
        chatModel.setContent(chat.getContent());
        chatModel.setDate(chat.getDate());
        chatModel.setFavorite(chat.isFavorite());
        chatModel.setFavoriteCount(chat.getFavoriteCount());
        chatModel.setLike(chat.isLike());
        chatModel.setLikeCount(chat.getLikeCount());
        chatModel.setPictureUrls(chat.getPictureUrls());
        chatModel.setTitle(chat.getTitle());
        chatModel.setWatchCount(chat.getWatchCount());

        return chatModel;
    }

    public List<ChatModel> transform(List<Chat> chatList) {
        List<ChatModel> chatModelList = new ArrayList<>(chatList.size());
        ChatModel chatModel;
        for (Chat chat : chatList) {
            chatModel = transform(chat);
            if (chat != null) {
                chatModelList.add(chatModel);
            }
        }

        return chatModelList;
    }
}
