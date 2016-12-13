package cn.canlnac.onlinecourse.domain;

import java.util.Date;

/**
 * 回复评论.
 */

public class Reply {
    private int id;

    private Date date;

    private Login toUser;

    private Login author;

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

    public Login getToUser() {
        return toUser;
    }

    public void setToUser(Login toUser) {
        this.toUser = toUser;
    }

    public Login getAuthor() {
        return author;
    }

    public void setAuthor(Login author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
