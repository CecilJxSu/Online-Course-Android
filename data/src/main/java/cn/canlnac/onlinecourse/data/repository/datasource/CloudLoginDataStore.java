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
package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.cache.LoginCache;
import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;

import rx.Observable;
import rx.functions.Action1;

/**
 * {@link LoginDataStore} implementation based on connections to the api (Cloud).
 */
class CloudLoginDataStore implements LoginDataStore {

    private final RestApi restApi;
    private final LoginCache loginCache;

    private final Action1<LoginEntity> saveToCacheAction = loginEnity -> {
        if (loginEnity != null) {
            CloudLoginDataStore.this.loginCache.put(loginEnity);
        }
    };

    /**
     * Construct a {@link LoginDataStore} based on connections to the api (Cloud).
     *
     * @param restApi The {@link RestApi} implementation to use.
     * @param loginCache A {@link LoginCache} to cache data retrieved from the api.
     */
    CloudLoginDataStore(RestApi restApi, LoginCache loginCache) {
        this.restApi = restApi;
        this.loginCache = loginCache;
    }

    @Override
    public Observable<LoginEntity> login(String username, String password) {
        return this.restApi.login(username, password);
    }
}
