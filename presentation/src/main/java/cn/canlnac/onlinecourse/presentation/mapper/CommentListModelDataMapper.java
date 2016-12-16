package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.CommentList;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CommentListModel;

@PerActivity
public class CommentListModelDataMapper {
    private final CommentModelDataMapper commentModelDataMapper;
    @Inject
    public CommentListModelDataMapper(CommentModelDataMapper commentModelDataMapper) {
        this.commentModelDataMapper = commentModelDataMapper;
    }

    public CommentListModel transform(CommentList commentList) {
        if (commentList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CommentListModel commentListModel = new CommentListModel();
        commentListModel.setTotal(commentList.getTotal());
        commentListModel.setComments(commentModelDataMapper.transform(commentList.getComments()));

        return commentListModel;
    }
}
