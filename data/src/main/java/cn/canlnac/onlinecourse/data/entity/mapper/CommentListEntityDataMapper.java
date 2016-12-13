package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.CommentListEntity;
import cn.canlnac.onlinecourse.domain.CommentList;

/**
 * 评论列表实体类转换.
 */
@Singleton
public class CommentListEntityDataMapper {
    private CommentEntityDataMapper commentEntityDataMapper;

    @Inject
    public CommentListEntityDataMapper(CommentEntityDataMapper commentEntityDataMapper) {
        this.commentEntityDataMapper = commentEntityDataMapper;
    }

    /**
     * 转换
     * @param commentListEntity 评论列表实体类
     * @return
     */
    public CommentList transform(CommentListEntity commentListEntity) {
        CommentList commentList = null;
        if (commentListEntity != null) {
            commentList = new CommentList();
            commentList.setTotal(commentListEntity.getTotal());
            commentList.setComments(commentEntityDataMapper.transform(commentListEntity.getComments()));
        }
        return commentList;
    }
}
