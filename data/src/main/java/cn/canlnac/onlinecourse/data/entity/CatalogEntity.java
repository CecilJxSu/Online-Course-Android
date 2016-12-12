package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 目录.
 */

public class CatalogEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private Date date;

    @SerializedName("courseId")
    private int courseId;

    @SerializedName("index")
    private int index;

    @SerializedName("name")
    private String name;

    @SerializedName("introduction")
    private String introduction;

    @SerializedName("url")
    private String url;

    @SerializedName("duration")
    private long duration;

    @SerializedName("previewImage")
    private String previewImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }
}
