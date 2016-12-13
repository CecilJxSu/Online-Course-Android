package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.CommentEntity;
import cn.canlnac.onlinecourse.domain.Comment;

/**
 * 评论实体类转换.
 */
@Singleton
public class CommentEntityDataMapper {
    private LoginEntityDataMapper loginEntityDataMapper;

    private ReplyEntityDataMapper replyEntityDataMapper;

    @Inject
    public CommentEntityDataMapper(LoginEntityDataMapper loginEntityDataMapper, ReplyEntityDataMapper replyEntityDataMapper) {
        this.loginEntityDataMapper = loginEntityDataMapper;
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
            comment.setAuthor(loginEntityDataMapper.transform(commentEntity.getAuthor()));
            comment.setContent(commentEntity.getContent());
            comment.setDate(commentEntity.getDate());
            comment.setId(commentEntity.getId());
            comment.setLike(commentEntity.isLike());
            comment.setLikeCount(commentEntity.getLikeCount());
            comment.setReplies(replyEntityDataMapper.transform(commentEntity.getReplies()));
            comment.setReplyCount(commentEntity.getReplyCount());
        }
        return comment;
    }
}
