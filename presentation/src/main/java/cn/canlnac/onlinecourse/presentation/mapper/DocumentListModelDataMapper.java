package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.DocumentList;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.DocumentListModel;

@PerActivity
public class DocumentListModelDataMapper {
    private final DocumentModelDataMapper documentModelDataMapper;
    @Inject
    public DocumentListModelDataMapper(DocumentModelDataMapper documentModelDataMapper) {
        this.documentModelDataMapper = documentModelDataMapper;
    }

    public DocumentListModel transform(DocumentList documentList) {
        if (documentList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        DocumentListModel documentListModel = new DocumentListModel();
        documentListModel.setTotal(documentList.getTotal());
        documentListModel.setDocuments(documentModelDataMapper.transform(documentList.getDocuments()));

        return documentListModel;
    }
}
