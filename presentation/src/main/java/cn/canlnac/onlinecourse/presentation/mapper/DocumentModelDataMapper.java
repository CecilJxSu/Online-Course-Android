package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Document;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.DocumentModel;

@PerActivity
public class DocumentModelDataMapper {
    @Inject
    public DocumentModelDataMapper() {}

    public DocumentModel transform(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        DocumentModel documentModel = new DocumentModel();
        documentModel.setName(document.getName());
        documentModel.setId(document.getId());
        documentModel.setDate(document.getDate());
        documentModel.setSize(document.getSize());
        documentModel.setTargetId(document.getTargetId());
        documentModel.setTargetType(document.getTargetType());
        documentModel.setType(document.getType());
        documentModel.setUrl(document.getUrl());

        return documentModel;
    }

    public List<DocumentModel> transform(List<Document> documentList) {
        List<DocumentModel> documentModelList = new ArrayList<>(documentList.size());
        DocumentModel documentModel;
        for (Document document : documentList) {
            documentModel = transform(document);
            if (document != null) {
                documentModelList.add(documentModel);
            }
        }

        return documentModelList;
    }
}
