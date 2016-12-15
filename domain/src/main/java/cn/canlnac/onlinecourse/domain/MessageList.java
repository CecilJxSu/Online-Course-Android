package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 消息列表.
 */

public class MessageList {
    private int total;

    private List<Message> messages;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
