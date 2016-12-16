package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 消息列表数据模型.
 */

public class MessageListModel {
    private int total;

    private List<MessageModel> messages;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageModel> messages) {
        this.messages = messages;
    }
}
