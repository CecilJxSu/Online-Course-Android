package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Register;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.RegisterModel;

@PerActivity
public class RegisterModelDataMapper {
    @Inject
    public RegisterModelDataMapper() {}

    public RegisterModel transform(Register register) {
        if (register == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        RegisterModel registerModel = new RegisterModel();
        registerModel.setUserId(register.getUserId());

        return registerModel;
    }
}
