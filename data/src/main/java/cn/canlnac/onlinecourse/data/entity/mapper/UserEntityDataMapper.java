package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.UserEntity;
import cn.canlnac.onlinecourse.domain.User;

/**
 * 用户实体类转换.
 */
@Singleton
public class UserEntityDataMapper {

    @Inject
    public UserEntityDataMapper() {}

    /**
     * 转换
     * @param userEntity 用户实体类
     * @return
     */
    public User transform(UserEntity userEntity) {
        User user = null;
        if (userEntity != null) {
            user = new User();
            user.setId(userEntity.getId());
            user.setDate(userEntity.getDate());
            user.setLockDate(userEntity.getLockDate());
            user.setLockEndDate(userEntity.getLockEndDate());
            user.setPassword(userEntity.getPassword());
            user.setStatus(userEntity.getStatus());
            user.setUsername(userEntity.getUsername());
            user.setUserStatus(userEntity.getUserStatus());
        }
        return user;
    }
}
