package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.User;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.UserModel;

@PerActivity
public class UserModelDataMapper {
    @Inject
    public UserModelDataMapper() {}

    public UserModel transform(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        UserModel userModel = new UserModel();
        userModel.setDate(user.getDate());
        userModel.setId(user.getId());
        userModel.setLockDate(user.getLockDate());
        userModel.setLockEndDate(user.getLockEndDate());
        userModel.setPassword(user.getPassword());
        userModel.setStatus(user.getStatus());
        userModel.setUsername(user.getUsername());
        userModel.setUserStatus(user.getUserStatus());

        return userModel;
    }
}
