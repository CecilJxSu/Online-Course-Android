package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 消息.
 */

public class MessageEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private long date;

    @SerializedName("type")
    private String type;

    @SerializedName("toUser")
    private SimpleUserEntity toUser;

    @SerializedName("fromUser")
    private SimpleUserEntity fromUser;

    @SerializedName("actionType")
    private String actionType;

    @SerializedName("position")
    private PositionEntity position;

    @SerializedName("content")
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

    public SimpleUserEntity getToUser() {
        return toUser;
    }

    public void setToUser(SimpleUserEntity toUser) {
        this.toUser = toUser;
    }

    public SimpleUserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(SimpleUserEntity fromUser) {
        this.fromUser = fromUser;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public PositionEntity getPosition() {
        return position;
    }

    public void setPosition(PositionEntity position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
