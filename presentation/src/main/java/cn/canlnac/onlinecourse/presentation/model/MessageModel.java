package cn.canlnac.onlinecourse.presentation.model;

import java.util.Date;

/**
 * 消息数据模型.
 */

public class MessageModel {
    private int id;

    private Date date;

    private String type;

    private LoginModel toUser;

    private LoginModel fromUser;

    private String actionType;

    private PositionModel position;

    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LoginModel getToUser() {
        return toUser;
    }

    public void setToUser(LoginModel toUser) {
        this.toUser = toUser;
    }

    public LoginModel getFromUser() {
        return fromUser;
    }

    public void setFromUser(LoginModel fromUser) {
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
