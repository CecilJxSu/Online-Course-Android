package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 回复评论.
 */

public class ReplyEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private Date date;

    @SerializedName("toUser")
    private LoginEntity toUser;

    @SerializedName("author")
    private LoginEntity author;

    @SerializedName("content")
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

    public LoginEntity getToUser() {
        return toUser;
    }

    public void setToUser(LoginEntity toUser) {
        this.toUser = toUser;
    }

    public LoginEntity getAuthor() {
        return author;
    }

    public void setAuthor(LoginEntity author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
