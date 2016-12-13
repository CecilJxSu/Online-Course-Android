package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 文档列表.
 */

public class DocumentList {
    private int total;

    private List<Document> documents;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
