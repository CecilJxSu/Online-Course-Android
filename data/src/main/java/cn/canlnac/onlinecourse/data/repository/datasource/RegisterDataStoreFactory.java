package cn.canlnac.onlinecourse.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.net.RestApi;
import cn.canlnac.onlinecourse.data.net.RestApiImpl;

/**
 * 创建用户数据储存工厂类.
 */
@Singleton
public class RegisterDataStoreFactory {
    private final Context context;

    @Inject
    public RegisterDataStoreFactory(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    public RegisterDataStore create() {
        RestApi restApi = new RestApiImpl(this.context);
        return new CloudRegisterDataStore(restApi);
    }

}
