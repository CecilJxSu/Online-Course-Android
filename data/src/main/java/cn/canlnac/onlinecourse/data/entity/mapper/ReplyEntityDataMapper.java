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
    private LoginEntityDataMapper loginEntityDataMapper;

    @Inject
    public ReplyEntityDataMapper(LoginEntityDataMapper loginEntityDataMapper) {
        this.loginEntityDataMapper = loginEntityDataMapper;
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
            reply.setAuthor(loginEntityDataMapper.transform(replyEntity.getAuthor()));
            reply.setContent(replyEntity.getContent());
            reply.setToUser(loginEntityDataMapper.transform(replyEntity.getToUser()));
        }
        return reply;
    }

    /**
     * 转换列表
     * @param replyEntityList   回复实体类列表
     * @return
     */
    public List<Reply> transform(List<ReplyEntity> replyEntityList) {
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
