package cn.canlnac.onlinecourse.presentation.model;

/**
 * 消息数据模型.
 */

public class MessageModel {
    private int id;

    private long date;

    private String type;

    private SimpleUserModel toUser;

    private SimpleUserModel fromUser;

    private String actionType;

    private PositionModel position;

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

    public SimpleUserModel getToUser() {
        return toUser;
    }

    public void setToUser(SimpleUserModel toUser) {
        this.toUser = toUser;
    }

    public SimpleUserModel getFromUser() {
        return fromUser;
    }

    public void setFromUser(SimpleUserModel fromUser) {
        this.fromUser = fromUser;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public PositionModel getPositionModel() {
        return position;
    }

    public void setPositionModel(PositionModel position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
