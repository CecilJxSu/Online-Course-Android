package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.ReplyEntity;
import cn.canlnac.onlinecourse.domain.Reply;

/**
 * 回复评论实体类转换.
 */
@Singleton
public class ReplyEntityDataMapper {
    private SimpleUserEntityDataMapper simpleUserEntityDataMapper;

    @Inject
    public ReplyEntityDataMapper(SimpleUserEntityDataMapper simpleUserEntityDataMapper) {
        this.simpleUserEntityDataMapper = simpleUserEntityDataMapper;
    }

    /**
     * 转换
     * @param replyEntity 回复评论实体类
     * @return
     */
    public Reply transform(ReplyEntity replyEntity) {
        Reply reply = null;
        if (replyEntity != null) {
            reply = new Reply();
            reply.setId(replyEntity.getId());
            reply.setDate(replyEntity.getDate());
            reply.setAuthor(simpleUserEntityDataMapper.transform(replyEntity.getAuthor()));
            reply.setContent(replyEntity.getContent());
            reply.setToUser(simpleUserEntityDataMapper.transform(replyEntity.getToUser()));
        }
        return reply;
    }

    /**
     * 转换列表
     * @param replyEntityList   回复实体类列表
     * @return
     */
    public List<Reply> transform(List<ReplyEntity> replyEntityList) {
        if (replyEntityList == null) {
            replyEntityList = new ArrayList<>();
        }
        List<Reply> replyList = new ArrayList<>(replyEntityList.size());
        Reply reply;
        for (ReplyEntity replyEntity : replyEntityList) {
            reply = transform(replyEntity);
            if (reply != null) {
                replyList.add(reply);
            }
        }

        return replyList;
    }
}
