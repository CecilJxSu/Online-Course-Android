package cn.canlnac.onlinecourse.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.net.RestApi;
import cn.canlnac.onlinecourse.data.net.RestApiConnection;
import cn.canlnac.onlinecourse.data.net.RestApiImpl;

/**
 * 创建评论数据储存工厂类.
 */
@Singleton
public class CommentDataStoreFactory {
    private final Context context;
    private final RestApiConnection restApiConnection;

    @Inject
    public CommentDataStoreFactory(@NonNull Context context) {
        this.context = context.getApplicationContext();
        this.restApiConnection = new RestApiConnection(this.context);
    }

    public CommentDataStore create() {
        RestApi restApi = new RestApiImpl(this.context, restApiConnection);
        return new CloudCommentDataStore(restApi);
    }

}
