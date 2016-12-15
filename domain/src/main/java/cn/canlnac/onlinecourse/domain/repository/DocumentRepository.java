package cn.canlnac.onlinecourse.domain.repository;

import cn.canlnac.onlinecourse.domain.Document;
import rx.Observable;

/**
 * 文档接口接口.
 */
public interface DocumentRepository {
    /** 获取指定文档 */
    Observable<Document> getDocument(int documentId);
    /** 删除指定文档 */
    Observable<Void> deleteDocument(int documentId);
}
