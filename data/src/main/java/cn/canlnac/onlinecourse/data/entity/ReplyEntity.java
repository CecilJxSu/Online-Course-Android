package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 回复评论.
 */

public class ReplyEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private long date;

    @SerializedName("toUser")
    private SimpleUserEntity toUser;

    @SerializedName("author")
    private SimpleUserEntity author;

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

    public SimpleUserEntity getToUser() {
        return toUser;
    }

    public void setToUser(SimpleUserEntity toUser) {
        this.toUser = toUser;
    }

    public SimpleUserEntity getAuthor() {
        return author;
    }

    public void setAuthor(SimpleUserEntity author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
