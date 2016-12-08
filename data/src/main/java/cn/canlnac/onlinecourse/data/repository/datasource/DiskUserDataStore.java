package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.cache.EntityCache;
import cn.canlnac.onlinecourse.data.entity.ResponseEntity;
import cn.canlnac.onlinecourse.data.entity.UserEntity;
import rx.Observable;

/**
 * 从本地中获取数据.
 */

public class DiskUserDataStore implements UserDataStore {
    private final EntityCache<UserEntity> userCache;

    DiskUserDataStore(EntityCache<UserEntity> userCache) {
        this.userCache = userCache;
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
        return null;
    }
}
