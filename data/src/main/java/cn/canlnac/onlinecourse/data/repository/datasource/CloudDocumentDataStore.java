package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.entity.DocumentEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;
import rx.Observable;

/**
 * 从服务器中获取文档数据.
 */

public class CloudDocumentDataStore implements DocumentDataStore {
    private final RestApi restApi;

    public CloudDocumentDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<DocumentEntity> getDocument(int documentId) {
        return this.restApi.getDocument(documentId);
    }

    @Override
    public Observable<Void> deleteDocument(int documentId) {
        return this.restApi.deleteDocument(documentId);
    }
}
