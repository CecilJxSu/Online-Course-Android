package cn.canlnac.onlinecourse.domain;

/**
 * 回复评论.
 */

public class Reply {
    private int id;

    private long date;

    private SimpleUser toUser;

    private SimpleUser author;

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

    public SimpleUser getToUser() {
        return toUser;
    }

    public void setToUser(SimpleUser toUser) {
        this.toUser = toUser;
    }

    public SimpleUser getAuthor() {
        return author;
    }

    public void setAuthor(SimpleUser author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
