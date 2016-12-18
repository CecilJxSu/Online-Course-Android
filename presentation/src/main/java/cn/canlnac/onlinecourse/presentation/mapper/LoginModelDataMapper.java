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
