package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 文档列表数据模型.
 */

public class DocumentListModel {
    private int total;

    private List<DocumentModel> documents;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DocumentModel> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentModel> documents) {
        this.documents = documents;
    }
}
