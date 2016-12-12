package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 课程.
 */

public class CourseEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private Date date;

    @SerializedName("name")
    private String name;

    @SerializedName("introduction")
    private String introduction;

    @SerializedName("author")
    private LoginEntity author;

    @SerializedName("department")
    private String department;

    @SerializedName("watchCount")
    private int watchCount;

    @SerializedName("likeCount")
    private int likeCount;

    @SerializedName("commentCount")
    private int commentCount;

    @SerializedName("favoriteCount")
    private int favoriteCount;

    @SerializedName("isLike")
    private boolean isLike;

    @SerializedName("isFavorite")
    private boolean isFavorite;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public LoginEntity getAuthor() {
        return author;
    }

    public void setAuthor(LoginEntity author) {
        this.author = author;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(int watchCount) {
        this.watchCount = watchCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
