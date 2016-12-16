package cn.canlnac.onlinecourse.data.repository.datasource;

import android.support.annotation.Nullable;

import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.AnswerEntity;
import cn.canlnac.onlinecourse.data.entity.CatalogEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentListEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordEntity;
import cn.canlnac.onlinecourse.data.entity.QuestionEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;
import rx.Observable;

/**
 * 从服务器中获取目录数据.
 */

public class CloudCatalogDataStore implements CatalogDataStore {
    private final RestApi restApi;

    public CloudCatalogDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<CatalogEntity> getCatalog(int catalogId) {
        return this.restApi.getCatalog(catalogId);
    }

    @Override
    public Observable<Void> updateCatalog(int catalogId, Map<String, Object> catalog) {
        return this.restApi.updateCatalog(catalogId, catalog);
    }

    @Override
    public Observable<Void> deleteCatalog(int catalogId) {
        return this.restApi.deleteCatalog(catalogId);
    }

    @Override
    public Observable<Integer> createQuestion(int catalogId, Map<String, Object> question) {
        return this.restApi.createQuestion(catalogId, question);
    }

    @Override
    public Observable<Void> updateQuestion(int catalogId, Map<String, Object> question) {
        return this.restApi.updateQuestion(catalogId, question);
    }

    @Override
    public Observable<Void> deleteQuestion(int catalogId) {
        return this.restApi.deleteQuestion(catalogId);
    }

    @Override
    public Observable<QuestionEntity> getQuestion(int catalogId) {
        return this.restApi.getQuestion(catalogId);
    }

    @Override
    public Observable<LearnRecordEntity> getLearnRecord(int catalogId) {
        return this.restApi.getLearnRecord(catalogId);
    }

    @Override
    public Observable<Integer> createLearnRecord(int catalogId, Map<String, Object> learnRecord) {
        return this.restApi.createLearnRecord(catalogId, learnRecord);
    }

    @Override
    public Observable<Void> updateLearnRecord(int catalogId, Map<String, Object> learnRecord) {
        return this.restApi.updateLearnRecord(catalogId, learnRecord);
    }

    @Override
    public Observable<Integer> createDocumentInCatalog(int catalogId, Map<String, Object> document) {
        return this.restApi.createDocumentInCatalog(catalogId, document);
    }

    @Override
    public Observable<DocumentListEntity> getDocumentsInCatalog(int catalogId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return this.restApi.getDocumentsInCatalog(catalogId, start, count, sort);
    }

    @Override
    public Observable<AnswerEntity> getAnswer(int catalogId) {
        return this.restApi.getAnswer(catalogId);
    }

    @Override
    public Observable<Integer> createAnser(int catalogId, Map<String, Object> answer) {
        return this.restApi.createAnser(catalogId, answer);
    }
}
