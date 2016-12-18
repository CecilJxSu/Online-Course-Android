package cn.canlnac.onlinecourse.presentation.model;

import java.util.Date;

/**
 * 学习记录数据模型.
 */

public class LearnRecordModel {
    private int id;

    private Date date;

    private CatalogModel catalog;

    private LoginModel user;

    private float progress;

    private Date lastDate;

    private long lastPosition;

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

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public long getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(long lastPosition) {
        this.lastPosition = lastPosition;
    }
}
