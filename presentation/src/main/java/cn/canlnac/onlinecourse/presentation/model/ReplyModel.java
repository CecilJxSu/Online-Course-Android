package cn.canlnac.onlinecourse.presentation.model;

/**
 * 回复评论数据模型.
 */

public class ReplyModel {
    private int id;

    private long date;

    private LoginModel toUser;

    private LoginModel author;

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

    public LoginModel getToUser() {
        return toUser;
    }

    public void setToUser(LoginModel toUser) {
        this.toUser = toUser;
    }

    public LoginModel getAuthor() {
        return author;
    }

    public void setAuthor(LoginModel author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
