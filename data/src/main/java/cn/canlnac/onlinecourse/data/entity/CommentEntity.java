package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 评论.
 */

public class CommentEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private long date;

    @SerializedName("author")
    private LoginEntity author;

    @SerializedName("content")
    private String content;

    @SerializedName("pictureUrls")
    private List<String> pictureUrls;

    @SerializedName("likeCount")
    private int likeCount;

    @SerializedName("replyCount")
    private int replyCount;

    @SerializedName("replies")
    private List<ReplyEntity> replies;

    @SerializedName("isLike")
    private boolean isLike;

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

    public LoginEntity getAuthor() {
        return author;
    }

    public void setAuthor(LoginEntity author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public List<ReplyEntity> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyEntity> replies) {
        this.replies = replies;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
