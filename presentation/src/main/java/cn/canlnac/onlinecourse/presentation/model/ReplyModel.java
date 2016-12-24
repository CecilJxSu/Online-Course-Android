package cn.canlnac.onlinecourse.presentation.model;

/**
 * 回复评论数据模型.
 */

public class ReplyModel {
    private int id;

    private long date;

    private SimpleUserModel toUser;

    private SimpleUserModel author;

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

    public SimpleUserModel getToUser() {
        return toUser;
    }

    public void setToUser(SimpleUserModel toUser) {
        this.toUser = toUser;
    }

    public SimpleUserModel getAuthor() {
        return author;
    }

    public void setAuthor(SimpleUserModel author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
