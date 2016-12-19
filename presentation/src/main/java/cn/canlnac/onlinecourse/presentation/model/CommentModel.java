package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 评论数据模型.
 */

public class CommentModel {
    private int id;

    private long date;

    private LoginModel author;

    private String content;

    private List<String> pictureUrls;

    private int likeCount;

    private int replyCount;

    private List<ReplyModel> replies;

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

    public LoginModel getAuthor() {
        return author;
    }

    public void setAuthor(LoginModel author) {
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

    public int getReplyModelCount() {
        return replyCount;
    }

    public void setReplyModelCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public List<ReplyModel> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyModel> replies) {
        this.replies = replies;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
