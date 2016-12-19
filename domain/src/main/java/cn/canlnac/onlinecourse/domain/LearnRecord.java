package cn.canlnac.onlinecourse.domain;

/**
 * 学习记录.
 */

public class LearnRecord {
    private int id;

    private long date;

    private Catalog catalog;

    private Login user;

    private float progress;

    private long lastDate;

    private long lastPosition;

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

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public Login getUser() {
        return user;
    }

    public void setUser(Login user) {
        this.user = user;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public long getLastDate() {
        return lastDate;
    }

    public void setLastDate(long lastDate) {
        this.lastDate = lastDate;
    }

    public long getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(long lastPosition) {
        this.lastPosition = lastPosition;
    }
}
