package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.ProfileEntity;
import cn.canlnac.onlinecourse.data.net.RestApiConnection;
import cn.canlnac.onlinecourse.domain.Profile;

/**
 * 用户资料实体类转换.
 */
@Singleton
public class ProfileEntityDataMapper {

    @Inject
    public ProfileEntityDataMapper() {}

    /**
     * 转换
     * @param profileEntity 用户资料实体类
     * @return
     */
    public Profile transform(ProfileEntity profileEntity) {
        Profile profile = null;
        if (profileEntity != null) {
            profile = new Profile();
            profile.setDepartment(profileEntity.getDepartment());
            profile.setDormitoryAddress(profileEntity.getDormitoryAddress());
            profile.setEmail(profileEntity.getEmail());
            profile.setFollowing(profileEntity.isFollowing());
            profile.setGender(profileEntity.getGender());
            if (profileEntity.getIconUrl() != null && profileEntity.getIconUrl().startsWith("http")) {
                profile.setIconUrl(profileEntity.getIconUrl());
            } else {
                profile.setIconUrl(RestApiConnection.API_FILE + "/" + profileEntity.getIconUrl());
            }
            profile.setMajor(profileEntity.getMajor());
            profile.setNickname(profileEntity.getNickname());
            profile.setPhone(profileEntity.getPhone());
            profile.setRealname(profileEntity.getRealname());
            profile.setUniversityId(profileEntity.getUniversityId());
            profile.setUserStatus(profileEntity.getUserStatus());
        }
        return profile;
    }
}
