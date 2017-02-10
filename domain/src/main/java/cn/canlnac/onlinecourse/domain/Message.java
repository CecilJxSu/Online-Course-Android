package cn.canlnac.onlinecourse.domain;

/**
 * 消息.
 */

public class Message {
    private int id;

    private long date;

    private String type;

    private SimpleUser toUser;

    private SimpleUser fromUser;

    private String actionType;

    private Position position;

    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SimpleUser getToUser() {
        return toUser;
    }

    public void setToUser(SimpleUser toUser) {
        this.toUser = toUser;
    }

    public SimpleUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(SimpleUser fromUser) {
        this.fromUser = fromUser;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
