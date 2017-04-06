package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.FollowerEntity;
import cn.canlnac.onlinecourse.domain.Follower;

/**
 * 关注/粉丝实体类转换.
 */
@Singleton
public class FollowerEntityDataMapper {
    private LoginEntityDataMapper loginEntityDataMapper;

    @Inject
    public FollowerEntityDataMapper(LoginEntityDataMapper loginEntityDataMapper) {
        this.loginEntityDataMapper = loginEntityDataMapper;
    }

    /**
     * 转换
     * @param followerEntity 关注/粉丝实体类
     * @return
     */
    public Follower transform(FollowerEntity followerEntity) {
        Follower follower = null;
        if (followerEntity != null) {
            follower = new Follower();
            follower.setTotal(followerEntity.getTotal());
            follower.setUsers(loginEntityDataMapper.transform(followerEntity.getUsers()));
        }
        return follower;
    }
}
