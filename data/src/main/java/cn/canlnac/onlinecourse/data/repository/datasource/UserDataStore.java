package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.entity.ResponseEntity;
import rx.Observable;

/**
 * 用户数据储存.
 */

public interface UserDataStore {
    /**
     * 用户注册
     * @param username  用户名
     * @param password  密码
     * @param email     邮箱
     * @return
     */
    Observable<ResponseEntity> register(String username, String password, String email);
}
