package cn.canlnac.onlinecourse.data.repository.datasource;

import rx.Observable;

import cn.canlnac.onlinecourse.data.entity.LoginEntity;

/**
 * 决定从何处检索数据接口
 */

public interface LoginDataStore {
    Observable<LoginEntity> login(String username, String password);
}
