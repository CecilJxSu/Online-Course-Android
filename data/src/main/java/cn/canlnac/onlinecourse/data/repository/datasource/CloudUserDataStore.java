package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.cache.EntityCache;
import cn.canlnac.onlinecourse.data.entity.ResponseEntity;
import cn.canlnac.onlinecourse.data.entity.UserEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;
import rx.Observable;

/**
 * 从服务器中获取用户数据.
 */

public class CloudUserDataStore implements UserDataStore {
    private final RestApi restApi;
    private final EntityCache<UserEntity> entityCache;

    /**
     * 构造函数.
     * @param restApi       网络接口.
     * @param entityCache   缓存接口.
     */
    CloudUserDataStore(RestApi restApi, EntityCache<UserEntity> entityCache) {
        this.restApi = restApi;
        this.entityCache = entityCache;
    }

    /**
     * 用户注册
     * @param username  用户名
     * @param password  密码
     * @param email     邮箱
     * @return
     */
    @Override
    public Observable<ResponseEntity> register(String username, String password, String email) {
        return this.restApi.register(username, password, email);
    }
}
