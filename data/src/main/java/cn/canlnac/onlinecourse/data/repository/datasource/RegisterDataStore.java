package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import rx.Observable;

/**
 * 用户数据储存.
 */

public interface RegisterDataStore {
    /**
     * 用户注册
     * @param username  用户名
     * @param password  密码
     * @return
     */
    Observable<RegisterEntity> register(String username, String password);
}
