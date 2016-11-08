package cn.canlnac.onlinecourse.domain;

import java.util.Date;

/**
 * 课程.
 */

public class Course {
    private final int id;
    private Date date;
    private String name;
    private String introduction;
    private BasicUser author;
    private String department;
    private int watchCount;
    private int likeCount;
    private int commentCount;
    private int favoriteCount;
    private boolean isLike;
    private boolean isFavorite;

    public Course(int courseId) {
        id = courseId;
    }

    public int getId() {
        return id;
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

    public BasicUser getAuthor() {
        return author;
    }

    public void setAuthor(BasicUser author) {
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Course Details *****\n");
        stringBuilder.append("id=" + this.getId() + "\n");
        stringBuilder.append("date=" + this.getDate() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("introduction=" + this.getIntroduction() + "\n");
        stringBuilder.append("author=\n" + this.getAuthor() + "\n\n");
        stringBuilder.append("department=" + this.getDepartment() + "\n");
        stringBuilder.append("watchCount=" + this.getWatchCount() + "\n");
        stringBuilder.append("likeCount=" + this.getLikeCount() + "\n");
        stringBuilder.append("commentCount=" + this.getCommentCount() + "\n");
        stringBuilder.append("favoriteCount=" + this.getFavoriteCount() + "\n");
        stringBuilder.append("isLike=" + this.isLike() + "\n");
        stringBuilder.append("isFavorite=" + this.isFavorite() + "\n");
        stringBuilder.append("**************************\n");

        return stringBuilder.toString();
    }
}
