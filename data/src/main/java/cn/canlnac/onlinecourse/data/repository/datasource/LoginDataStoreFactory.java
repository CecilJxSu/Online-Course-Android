/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.canlnac.onlinecourse.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;
import cn.canlnac.onlinecourse.data.cache.LoginCache;
import cn.canlnac.onlinecourse.data.entity.mapper.LoginEntityJsonMapper;
import cn.canlnac.onlinecourse.data.net.RestApi;
import cn.canlnac.onlinecourse.data.net.RestApiImpl;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link LoginDataStore}.
 */
@Singleton
public class LoginDataStoreFactory {

    private final Context context;
    private final LoginCache loginCache;

    @Inject
    public LoginDataStoreFactory(@NonNull Context context, @NonNull LoginCache loginCache) {
        this.context = context.getApplicationContext();
        this.loginCache = loginCache;
    }

    /**
     * Create {@link LoginDataStore} from a login id.
     */
    public LoginDataStore create() {
        LoginDataStore loginDataStore;

        if (!this.loginCache.isExpired() && this.loginCache.isCached()) {
            loginDataStore = new DiskLoginDataStore(this.loginCache);
        } else {
            loginDataStore = createCloudDataStore();
        }

        return loginDataStore;
    }

    /**
     * Create {@link LoginDataStore} to retrieve data from the Cloud.
     */
    public LoginDataStore createCloudDataStore() {
        LoginEntityJsonMapper loginEntityJsonMapper = new LoginEntityJsonMapper();
        RestApi restApi = new RestApiImpl(this.context, loginEntityJsonMapper);

        return new CloudLoginDataStore(restApi, this.loginCache);
    }
}
