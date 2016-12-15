package cn.canlnac.onlinecourse.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.net.RestApi;
import cn.canlnac.onlinecourse.data.net.RestApiConnection;
import cn.canlnac.onlinecourse.data.net.RestApiImpl;

/**
 * 创建文档数据储存工厂类.
 */
@Singleton
public class DocumentDataStoreFactory {
    private final Context context;
    private final RestApiConnection restApiConnection;

    @Inject
    public DocumentDataStoreFactory(@NonNull Context context) {
        this.context = context.getApplicationContext();
        this.restApiConnection = new RestApiConnection(this.context);
    }

    public DocumentDataStore create() {
        RestApi restApi = new RestApiImpl(this.context, restApiConnection);
        return new CloudDocumentDataStore(restApi);
    }

}
