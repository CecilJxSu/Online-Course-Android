package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.CommentEntity;
import cn.canlnac.onlinecourse.domain.Comment;

/**
 * 评论实体类转换.
 */
@Singleton
public class CommentEntityDataMapper {
    private SimpleUserEntityDataMapper simpleUserEntityDataMapper;

    private ReplyEntityDataMapper replyEntityDataMapper;

    @Inject
    public CommentEntityDataMapper(SimpleUserEntityDataMapper simpleUserEntityDataMapper, ReplyEntityDataMapper replyEntityDataMapper) {
        this.simpleUserEntityDataMapper = simpleUserEntityDataMapper;
        this.replyEntityDataMapper = replyEntityDataMapper;
    }

    /**
     * 转换
     * @param commentEntity 评论实体类
     * @return
     */
    public Comment transform(CommentEntity commentEntity) {
        Comment comment = null;
        if (commentEntity != null) {
            comment = new Comment();
            comment.setPictureUrls(commentEntity.getPictureUrls());
            comment.setAuthor(simpleUserEntityDataMapper.transform(commentEntity.getAuthor()));
            comment.setContent(commentEntity.getContent());
            comment.setDate(commentEntity.getDate());
            comment.setId(commentEntity.getId());
            comment.setLike(commentEntity.isLike());
            comment.setLikeCount(commentEntity.getLikeCount());
            comment.setReplies(replyEntityDataMapper.transform(commentEntity.getReplies()));
            comment.setReplyCount(commentEntity.getReplyCount());
            comment.setReply(commentEntity.isReply());
        }
        return comment;
    }

    /**
     * 转换列表
     * @param commentEntityList    评论列表实体类列表
     * @return
     */
    public List<Comment> transform(List<CommentEntity> commentEntityList) {
        List<Comment> commentList = new ArrayList<>(commentEntityList.size());
        Comment comment;
        for (CommentEntity commentEntity : commentEntityList) {
            comment = transform(commentEntity);
            if (comment != null) {
                commentList.add(comment);
            }
        }

        return commentList;
    }
}
