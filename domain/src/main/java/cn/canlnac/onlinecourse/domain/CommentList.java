package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 评论列表.
 */

public class CommentList {
    private int total;

    private List<Comment> comments;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
