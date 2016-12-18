package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 评论列表数据模型.
 */

public class CommentListModel {
    private int total;

    private List<CommentModel> comments;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }
}
