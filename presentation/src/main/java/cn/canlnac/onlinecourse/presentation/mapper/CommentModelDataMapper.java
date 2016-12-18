package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Comment;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;

@PerActivity
public class CommentModelDataMapper {
    private final LoginModelDataMapper loginModelDataMapper;
    private final ReplyModelDataMapper replyModelDataMapper;

    @Inject
    public CommentModelDataMapper(LoginModelDataMapper loginModelDataMapper, ReplyModelDataMapper replyModelDataMapper) {
        this.loginModelDataMapper = loginModelDataMapper;
        this.replyModelDataMapper = replyModelDataMapper;
    }

    public CommentModel transform(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CommentModel commentModel = new CommentModel();
        commentModel.setId(comment.getId());
        commentModel.setPictureUrls(comment.getPictureUrls());
        commentModel.setAuthor(loginModelDataMapper.transform(comment.getAuthor()));
        commentModel.setContent(comment.getContent());
        commentModel.setDate(comment.getDate());
        commentModel.setLike(comment.isLike());
        commentModel.setLikeCount(comment.getLikeCount());
        commentModel.setReplies(replyModelDataMapper.transform(comment.getReplies()));
        commentModel.setReplyModelCount(comment.getReplyCount());

        return commentModel;
    }

    public List<CommentModel> transform(List<Comment> chatList) {
        List<CommentModel> chatModelList = new ArrayList<>(chatList.size());
        CommentModel chatModel;
        for (Comment chat : chatList) {
            chatModel = transform(chat);
            if (chat != null) {
                chatModelList.add(chatModel);
            }
        }

        return chatModelList;
    }
}
