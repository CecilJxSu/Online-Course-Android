package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 话题列表数据模型.
 */

public class ChatListModel {
    private int total;

    private List<ChatModel> chats;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChatModel> getChats() {
        return chats;
    }

    public void setChats(List<ChatModel> chats) {
        this.chats = chats;
    }
}
