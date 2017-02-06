package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.DocumentEntity;
import cn.canlnac.onlinecourse.data.net.RestApiConnection;
import cn.canlnac.onlinecourse.domain.Document;

/**
 * 文档实体类转换.
 */
@Singleton
public class DocumentEntityDataMapper {

    @Inject
    public DocumentEntityDataMapper() {}

    /**
     * 转换
     * @param documentEntity 文档实体类
     * @return
     */
    public Document transform(DocumentEntity documentEntity) {
        Document document = null;
        if (documentEntity != null) {
            document = new Document();
            document.setId(documentEntity.getId());
            document.setName(documentEntity.getName());
            document.setDate(documentEntity.getDate());
            document.setSize(documentEntity.getSize());
            document.setTargetId(documentEntity.getTargetId());
            document.setTargetType(documentEntity.getTargetType());
            document.setType(documentEntity.getType());
            if (documentEntity.getUrl() != null && documentEntity.getUrl().startsWith("http")) {
                document.setUrl(documentEntity.getUrl());
            } else {
                document.setUrl(RestApiConnection.API_FILE + "/" + documentEntity.getUrl());
            }
        }
        return document;
    }

    /**
     * 转换列表
     * @param documentEntityList    文档实体类列表
     * @return
     */
    public List<Document> transform(List<DocumentEntity> documentEntityList) {
        List<Document> documentList = new ArrayList<>(documentEntityList.size());
        Document document;
        for (DocumentEntity documentEntity : documentEntityList) {
            document = transform(documentEntity);
            if (document != null) {
                documentList.add(document);
            }
        }

        return documentList;
    }
}
