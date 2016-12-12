package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * 话题.
 */

public class ChatEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private Date date;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("author")
    private LoginEntity author;

    @SerializedName("pictureUrls")
    private List<String> pictureUrls;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LoginEntity getAuthor() {
        return author;
    }

    public void setAuthor(LoginEntity author) {
        this.author = author;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
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
