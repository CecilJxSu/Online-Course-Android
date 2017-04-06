package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 学习记录.
 */

public class LearnRecordEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private long date;

    @SerializedName("catalog")
    private CatalogEntity catalog;

    @SerializedName("user")
    private LoginEntity user;

    @SerializedName("progress")
    private float progress;

    @SerializedName("lastDate")
    private long lastDate;

    @SerializedName("lastPosition")
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

    public CatalogEntity getCatalog() {
        return catalog;
    }

    public void setCatalog(CatalogEntity catalog) {
        this.catalog = catalog;
    }

    public LoginEntity getUser() {
        return user;
    }

    public void setUser(LoginEntity user) {
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
