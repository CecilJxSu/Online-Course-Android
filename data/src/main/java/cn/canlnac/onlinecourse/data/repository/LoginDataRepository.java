/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.canlnac.onlinecourse.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.LoginEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.LoginDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.LoginDataStoreFactory;
import cn.canlnac.onlinecourse.domain.Login;
import cn.canlnac.onlinecourse.domain.repository.LoginRepository;
import rx.Observable;

/**
 * {@link LoginRepository} for retrieving user data.
 */
@Singleton
public class LoginDataRepository implements LoginRepository {

    private final LoginDataStoreFactory loginDataStoreFactory;
    private final LoginEntityDataMapper loginEntityDataMapper;

    /**
     * Constructs a {@link LoginRepository}.
     *
     * @param dataStoreFactory A factory to construct different data source implementations.
     * @param loginEntityDataMapper {@link LoginEntityDataMapper}.
     */
    @Inject
    public LoginDataRepository(LoginDataStoreFactory dataStoreFactory,
                              LoginEntityDataMapper loginEntityDataMapper) {
        this.loginDataStoreFactory = dataStoreFactory;
        this.loginEntityDataMapper = loginEntityDataMapper;
    }

    @Override
    public Observable<Login> login(String username, String password) {
        final LoginDataStore loginDataStore = this.loginDataStoreFactory.create();
        return loginDataStore.login(username, password).map(this.loginEntityDataMapper::transform);
    }
}
