package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.DocumentListEntity;
import cn.canlnac.onlinecourse.domain.DocumentList;

/**
 * 文档列表实体类转换.
 */
@Singleton
public class DocumentListEntityDataMapper {
    private DocumentEntityDataMapper documentEntityDataMapper;

    @Inject
    public DocumentListEntityDataMapper(DocumentEntityDataMapper documentEntityDataMapper) {
        this.documentEntityDataMapper = documentEntityDataMapper;
    }

    /**
     * 转换
     * @param documentListEntity 文档列表实体类
     * @return
     */
    public DocumentList transform(DocumentListEntity documentListEntity) {
        DocumentList documentList = null;
        if (documentListEntity != null) {
            documentList = new DocumentList();
            documentList.setTotal(documentListEntity.getTotal());
            documentList.setDocuments(documentEntityDataMapper.transform(documentListEntity.getDocuments()));
        }
        return documentList;
    }
}
