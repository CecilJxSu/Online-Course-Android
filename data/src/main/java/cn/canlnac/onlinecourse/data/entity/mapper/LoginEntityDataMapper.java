/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.domain.Login;

/**
 * Mapper class used to transform {@link LoginEntity} (in the data layer) to {@link Login} in the
 * domain layer.
 */
@Singleton
public class LoginEntityDataMapper {

    @Inject
    public LoginEntityDataMapper() {
    }

    /**
     * Transform a {@link LoginEntity} into an {@link Login}.
     *
     * @param loginEntity Object to be transformed.
     * @return {@link Login} if valid {@link LoginEntity} otherwise null.
     */
    public Login transform(LoginEntity loginEntity) {
        Login login = null;
        if (loginEntity != null) {
            login = new Login();
            login.setGender(loginEntity.getGender());
            login.setIconUrl(loginEntity.getIconUrl());
            login.setId(loginEntity.getId());
            login.setLockDate(loginEntity.getLockDate());
            login.setLockEndDate(loginEntity.getLockEndDate());
            login.setNickname(loginEntity.getNickname());
            login.setUserStatus(loginEntity.getUserStatus());
        }

        return login;
    }
}
