package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.domain.Login;

/**
 * 登录实体类转换.
 */
@Singleton
public class LoginEntityDataMapper {

    @Inject
    public LoginEntityDataMapper() {}

    /**
     * 转换
     * @param loginEntity 登录实体类
     * @return
     */
    public Login transform(LoginEntity loginEntity) {
        Login login = null;
        if (loginEntity != null) {
            login = new Login();
            login.setUserStatus(loginEntity.getUserStatus());
            login.setNickname(loginEntity.getNickname());
            login.setId(loginEntity.getId());
            login.setLockEndDate(loginEntity.getLockEndDate());
            login.setGender(loginEntity.getGender());
            login.setIconUrl(loginEntity.getIconUrl());
            login.setJwt(loginEntity.getJwt());
            login.setLockDate(loginEntity.getLockDate());
        }
        return login;
    }

    /**
     * 转换列表
     * @param loginEntityList    登录实体类列表
     * @return
     */
    public List<Login> transform(List<LoginEntity> loginEntityList) {
        List<Login> loginList = new ArrayList<>(loginEntityList.size());
        Login login;
        for (LoginEntity loginEntity : loginEntityList) {
            login = transform(loginEntity);
            if (login != null) {
                loginList.add(login);
            }
        }

        return loginList;
    }
}
