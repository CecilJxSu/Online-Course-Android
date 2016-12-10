package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;
import rx.Observable;

/**
 * 从服务器中获取用户数据.
 */

public class CloudRegisterDataStore implements RegisterDataStore {
    private final RestApi restApi;

    /**
     * 构造函数.
     * @param restApi       网络接口.
     */
    CloudRegisterDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    /**
     * 用户注册
     * @param username  用户名
     * @param password  密码
     * @return
     */
    @Override
    public Observable<RegisterEntity> register(String username, String password) {
        return this.restApi.register(username, password);
    }
}
