package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Follower;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.FollowerModel;

@PerActivity
public class FollowerModelDataMapper {
    private final LoginModelDataMapper loginModelDataMapper;
    @Inject
    public FollowerModelDataMapper(LoginModelDataMapper loginModelDataMapper) {
        this.loginModelDataMapper = loginModelDataMapper;
    }

    public FollowerModel transform(Follower follower) {
        if (follower == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        FollowerModel followerModel = new FollowerModel();
        followerModel.setTotal(follower.getTotal());
        followerModel.setUsers(loginModelDataMapper.transform(follower.getUsers()));

        return followerModel;
    }
}
