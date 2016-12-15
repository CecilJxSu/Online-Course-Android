package cn.canlnac.onlinecourse.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.DocumentEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.DocumentDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.DocumentDataStoreFactory;
import cn.canlnac.onlinecourse.domain.Document;
import cn.canlnac.onlinecourse.domain.repository.DocumentRepository;
import rx.Observable;

/**
 * 文档数据接口，提供给domain调用.
 */
@Singleton
public class DocumentDataRepository implements DocumentRepository {
    private final DocumentDataStore documentDataStore;

    private final DocumentEntityDataMapper documentEntityDataMapper;

    @Inject
    public DocumentDataRepository(
            DocumentDataStoreFactory documentDataStoreFactory,
            DocumentEntityDataMapper documentEntityDataMapper
    ) {
        this.documentDataStore = documentDataStoreFactory.create();
        this.documentEntityDataMapper = documentEntityDataMapper;
    }

    @Override
    public Observable<Document> getDocument(int documentId) {
        return documentDataStore.getDocument(documentId).map(documentEntityDataMapper::transform);
    }

    @Override
    public Observable<Void> deleteDocument(int documentId) {
        return documentDataStore.deleteDocument(documentId);
    }
}
