package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Profile;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.ProfileModel;

@PerActivity
public class ProfileModelDataMapper {
    @Inject
    public ProfileModelDataMapper() {}

    public ProfileModel transform(Profile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ProfileModel profileModel = new ProfileModel();
        profileModel.setDepartment(profile.getDepartment());
        profileModel.setDormitoryAddress(profile.getDormitoryAddress());
        profileModel.setEmail(profile.getEmail());
        profileModel.setFollowing(profile.isFollowing());
        profileModel.setGender(profile.getGender());
        profileModel.setIconUrl(profile.getIconUrl());
        profileModel.setMajor(profile.getMajor());
        profileModel.setNickname(profile.getNickname());
        profileModel.setPhone(profile.getPhone());
        profileModel.setRealname(profile.getRealname());
        profileModel.setUniversityId(profile.getUniversityId());
        profileModel.setUserStatus(profile.getUserStatus());

        return profileModel;
    }
}
