package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Comment;
import cn.canlnac.onlinecourse.domain.Reply;
import cn.canlnac.onlinecourse.domain.SimpleUser;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;

@PerActivity
public class CommentModelDataMapper {
    private final SimpleUserModelDataMapper simpleUserModelDataMapper;
    private final ReplyModelDataMapper replyModelDataMapper;

    @Inject
    public CommentModelDataMapper(SimpleUserModelDataMapper simpleUserModelDataMapper, ReplyModelDataMapper replyModelDataMapper) {
        this.simpleUserModelDataMapper = simpleUserModelDataMapper;
        this.replyModelDataMapper = replyModelDataMapper;
    }

    public CommentModel transform(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CommentModel commentModel = new CommentModel();
        commentModel.setId(comment.getId());
        commentModel.setPictureUrls(comment.getPictureUrls());

        if (comment.getAuthor() == null) {
            comment.setAuthor(new SimpleUser());
        }

        commentModel.setAuthor(simpleUserModelDataMapper.transform(comment.getAuthor()));
        commentModel.setContent(comment.getContent());
        commentModel.setDate(comment.getDate());
        commentModel.setLike(comment.isLike());
        commentModel.setLikeCount(comment.getLikeCount());

        if (comment.getReplies() == null) {
            comment.setReplies(new ArrayList<Reply>());
        }
        commentModel.setReplies(replyModelDataMapper.transform(comment.getReplies()));
        commentModel.setReplyCount(comment.getReplyCount());
        commentModel.setReply(comment.isReply());

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
