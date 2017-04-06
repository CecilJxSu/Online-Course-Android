package cn.canlnac.onlinecourse.presentation.model;

/**
 * 学习记录数据模型.
 */

public class LearnRecordModel {
    private int id;

    private long date;

    private CatalogModel catalog;

    private LoginModel user;

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

    public CatalogModel getCatalogModel() {
        return catalog;
    }

    public void setCatalogModel(CatalogModel catalog) {
        this.catalog = catalog;
    }

    public LoginModel getUser() {
        return user;
    }

    public void setUser(LoginModel user) {
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
