package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 话题列表.
 */

public class ChatListEntity {
    @SerializedName("total")
    private int total;

    @SerializedName("chats")
    private List<ChatEntity> chats;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChatEntity> getChats() {
        return chats;
    }

    public void setChats(List<ChatEntity> chats) {
        this.chats = chats;
    }
}
