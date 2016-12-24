package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.SimpleUserEntity;
import cn.canlnac.onlinecourse.domain.SimpleUser;

/**
 * 用户基本信息实体类转换.
 */
@Singleton
public class SimpleUserEntityDataMapper {

    @Inject
    public SimpleUserEntityDataMapper() {}

    /**
     * 转换
     * @param simpleUserEntity 用户基本信息实体类
     * @return
     */
    public SimpleUser transform(SimpleUserEntity simpleUserEntity) {
        SimpleUser simpleUser = null;
        if (simpleUserEntity != null) {
            simpleUser = new SimpleUser();
            simpleUser.setId(simpleUserEntity.getId());
            simpleUser.setName(simpleUserEntity.getName());
            simpleUser.setIconUrl(simpleUserEntity.getIconUrl());
        }
        return simpleUser;
    }

    /**
     * 转换列表
     * @param simpleUserEntityList    用户基本信息实体类列表
     * @return
     */
    public List<SimpleUser> transform(List<SimpleUserEntity> simpleUserEntityList) {
        List<SimpleUser> loginList = new ArrayList<>(simpleUserEntityList.size());
        SimpleUser simpleUser;
        for (SimpleUserEntity simpleUserEntity : simpleUserEntityList) {
            simpleUser = transform(simpleUserEntity);
            if (simpleUser != null) {
                loginList.add(simpleUser);
            }
        }

        return loginList;
    }
}
