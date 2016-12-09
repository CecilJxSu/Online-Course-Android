package cn.canlnac.onlinecourse.domain.repository;

import cn.canlnac.onlinecourse.domain.Register;
import rx.Observable;

/**
 * 用户接口.
 */
public interface RegisterRepository {
    /**
     * 用户注册
     * @param username  用户名
     * @param password  密码
     * @param email     邮箱
     * @return
     */
    Observable<Register> register(String username, String password, String email);
}
