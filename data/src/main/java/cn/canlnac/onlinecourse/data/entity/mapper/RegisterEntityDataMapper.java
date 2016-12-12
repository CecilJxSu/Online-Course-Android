package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import cn.canlnac.onlinecourse.domain.Register;

/**
 * 注册实体类转换.
 */
@Singleton
public class RegisterEntityDataMapper {

    @Inject
    public RegisterEntityDataMapper() {}

    /**
     * 转换
     * @param registerEntity 注册实体类
     * @return
     */
    public Register transform(RegisterEntity registerEntity) {
        Register register = null;
        if (registerEntity != null) {
            register = new Register();
            register.setUserId(registerEntity.getUserId());
        }
        return register;
    }
}
