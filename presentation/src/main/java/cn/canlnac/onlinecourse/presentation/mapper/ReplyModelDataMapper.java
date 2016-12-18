package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Reply;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.ReplyModel;

@PerActivity
public class ReplyModelDataMapper {
    private final LoginModelDataMapper loginModelDataMapper;

    @Inject
    public ReplyModelDataMapper(LoginModelDataMapper loginModelDataMapper) {
        this.loginModelDataMapper = loginModelDataMapper;
    }

    public ReplyModel transform(Reply reply) {
        if (reply == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ReplyModel replyModel = new ReplyModel();
        replyModel.setId(reply.getId());
        replyModel.setDate(reply.getDate());
        replyModel.setAuthor(loginModelDataMapper.transform(reply.getAuthor()));
        replyModel.setContent(reply.getContent());
        replyModel.setToUser(loginModelDataMapper.transform(reply.getToUser()));

        return replyModel;
    }

    public List<ReplyModel> transform(List<Reply> replyList) {
        List<ReplyModel> replyModelList = new ArrayList<>(replyList.size());
        ReplyModel replyModel;
        for (Reply reply : replyList) {
            replyModel = transform(reply);
            if (reply != null) {
                replyModelList.add(replyModel);
            }
        }

        return replyModelList;
    }
}
