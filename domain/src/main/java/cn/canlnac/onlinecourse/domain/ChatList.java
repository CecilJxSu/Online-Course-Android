package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 话题列表.
 */

public class ChatList {
    private int total;

    private List<Chat> chats;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }
}
