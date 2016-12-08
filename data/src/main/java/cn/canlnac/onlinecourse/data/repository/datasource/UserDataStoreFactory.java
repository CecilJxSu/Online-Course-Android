package cn.canlnac.onlinecourse.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.cache.EntityCache;
import cn.canlnac.onlinecourse.data.entity.UserEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;
import cn.canlnac.onlinecourse.data.net.RestApiImpl;

/**
 * 创建用户数据储存工厂类.
 */
@Singleton
public class UserDataStoreFactory {
    private final Context context;
    private final EntityCache<UserEntity> userCache;

    @Inject
    public UserDataStoreFactory(@NonNull Context context, @NonNull EntityCache<UserEntity> userCache) {
        this.context = context.getApplicationContext();
        this.userCache = userCache;
    }

    public UserDataStore create(int userId) {
        UserDataStore userDataStore;

        if (!this.userCache.isExpired() && this.userCache.isCached(userId)) {
            userDataStore = new DiskUserDataStore(this.userCache);
        } else {
            userDataStore = createCloudDataStore();
        }

        return userDataStore;
    }

    /**
     * 创建 {@link CloudUserDataStore} 对象
     * @return
     */
    public UserDataStore createCloudDataStore() {
        RestApi restApi = new RestApiImpl(this.context);
        return new CloudUserDataStore(restApi, this.userCache);
    }

}
