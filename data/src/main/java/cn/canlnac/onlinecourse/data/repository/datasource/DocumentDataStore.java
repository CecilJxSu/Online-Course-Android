package cn.canlnac.onlinecourse.data.repository.datasource;

import cn.canlnac.onlinecourse.data.entity.DocumentEntity;
import rx.Observable;

/**
 * 文档数据储存.
 */

public interface DocumentDataStore {
    /** 获取指定文档 */
    Observable<DocumentEntity> getDocument(int documentId);
    /** 删除指定文档 */
    Observable<Void> deleteDocument(int documentId);
}
