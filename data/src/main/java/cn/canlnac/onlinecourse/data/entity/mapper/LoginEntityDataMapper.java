package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.data.net.RestApiConnection;
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
            if (loginEntity.getIconUrl() != null && loginEntity.getIconUrl().startsWith("http")) {
                login.setIconUrl(loginEntity.getIconUrl());
            } else {
                login.setIconUrl(RestApiConnection.API_FILE + "/" + loginEntity.getIconUrl());
            }

            login.setJwt(loginEntity.getJwt());
            login.setLockDate(loginEntity.getLockDate());
        }
        return login;
    }

    public LoginEntity transform(Login login) {
        LoginEntity loginEntity = null;
        if (login != null) {
            loginEntity = new LoginEntity();
            loginEntity.setUserStatus(login.getUserStatus());
            loginEntity.setNickname(login.getNickname());
            loginEntity.setId(login.getId());
            loginEntity.setLockEndDate(login.getLockEndDate());
            loginEntity.setGender(login.getGender());
            if (loginEntity.getIconUrl() != null && login.getIconUrl().startsWith("http")) {
                loginEntity.setIconUrl(login.getIconUrl());
            } else {
                loginEntity.setIconUrl(RestApiConnection.API_FILE + "/" + login.getIconUrl());
            }

            loginEntity.setJwt(login.getJwt());
            loginEntity.setLockDate(login.getLockDate());
        }
        return loginEntity;
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
