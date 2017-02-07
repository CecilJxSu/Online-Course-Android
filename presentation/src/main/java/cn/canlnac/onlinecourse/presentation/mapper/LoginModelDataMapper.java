package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Login;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.LoginModel;

@PerActivity
public class LoginModelDataMapper {
    @Inject
    public LoginModelDataMapper() {}

    public LoginModel transform(Login login) {
        if (login == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        LoginModel loginModel = new LoginModel();
        loginModel.setId(login.getId());
        loginModel.setJwt(login.getJwt());
        loginModel.setGender(login.getGender());
        loginModel.setIconUrl(login.getIconUrl());
        loginModel.setLockDate(login.getLockDate());
        loginModel.setLockEndDate(login.getLockEndDate());
        loginModel.setNickname(login.getNickname());
        loginModel.setUserStatus(login.getUserStatus());

        return loginModel;
    }

    public Login transform(LoginModel loginModel) {
        if (loginModel == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        Login login = new Login();
        login.setId(loginModel.getId());
        login.setJwt(loginModel.getJwt());
        login.setGender(loginModel.getGender());
        login.setIconUrl(loginModel.getIconUrl());
        login.setLockDate(loginModel.getLockDate());
        login.setLockEndDate(loginModel.getLockEndDate());
        login.setNickname(loginModel.getNickname());
        login.setUserStatus(loginModel.getUserStatus());

        return login;
    }

    public List<LoginModel> transform(List<Login> loginList) {
        List<LoginModel> loginModelList = new ArrayList<>(loginList.size());
        LoginModel loginModel;
        for (Login login : loginList) {
            loginModel = transform(login);
            if (login != null) {
                loginModelList.add(loginModel);
            }
        }

        return loginModelList;
    }
}
