package cn.canlnac.onlinecourse.data.repository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.AnswerEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.CatalogEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.DocumentListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.LearnRecordEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.QuestionEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.CatalogDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.CatalogDataStoreFactory;
import cn.canlnac.onlinecourse.domain.Answer;
import cn.canlnac.onlinecourse.domain.Catalog;
import cn.canlnac.onlinecourse.domain.DocumentList;
import cn.canlnac.onlinecourse.domain.LearnRecord;
import cn.canlnac.onlinecourse.domain.Question;
import cn.canlnac.onlinecourse.domain.repository.CatalogRepository;
import rx.Observable;

/**
 * 目录数据接口，提供给domain调用.
 */
@Singleton
public class CatalogDataRepository implements CatalogRepository {

    private final CatalogDataStore catalogDataStore;
    private final CatalogEntityDataMapper catalogEntityDataMapper;
    private final QuestionEntityDataMapper questionEntityDataMapper;
    private final LearnRecordEntityDataMapper learnRecordEntityDataMapper;
    private final DocumentListEntityDataMapper documentListEntityDataMapper;
    private final AnswerEntityDataMapper answerEntityDataMapper;


    @Inject
    public CatalogDataRepository(
            CatalogDataStoreFactory catalogDataStoreFactory,
            CatalogEntityDataMapper catalogEntityDataMapper,
            QuestionEntityDataMapper questionEntityDataMapper,
            LearnRecordEntityDataMapper learnRecordEntityDataMapper,
            DocumentListEntityDataMapper documentListEntityDataMapper,
            AnswerEntityDataMapper answerEntityDataMapper
    ) {
        this.catalogEntityDataMapper = catalogEntityDataMapper;
        this.questionEntityDataMapper = questionEntityDataMapper;
        this.learnRecordEntityDataMapper = learnRecordEntityDataMapper;
        this.documentListEntityDataMapper = documentListEntityDataMapper;
        this.answerEntityDataMapper = answerEntityDataMapper;
        catalogDataStore = catalogDataStoreFactory.create();
    }

    @Override
    public Observable<Catalog> getCatalog(int catalogId) {
        return catalogDataStore.getCatalog(catalogId).map(catalogEntityDataMapper::transform);
    }

    @Override
    public Observable<Void> updateCatalog(int catalogId, Map<String, Object> catalog) {
        return catalogDataStore.updateCatalog(catalogId, catalog);
    }

    @Override
    public Observable<Void> deleteCatalog(int catalogId) {
        return catalogDataStore.deleteCatalog(catalogId);
    }

    @Override
    public Observable<Integer> createQuestion(int catalogId, Map<String, Object> question) {
        return catalogDataStore.createQuestion(catalogId, question);
    }

    @Override
    public Observable<Void> updateQuestion(int catalogId, Map<String, Object> question) {
        return catalogDataStore.updateQuestion(catalogId, question);
    }

    @Override
    public Observable<Void> deleteQuestion(int catalogId) {
        return catalogDataStore.deleteQuestion(catalogId);
    }

    @Override
    public Observable<Question> getQuestion(int catalogId) {
        return catalogDataStore.getQuestion(catalogId).map(questionEntityDataMapper::transform);
    }

    @Override
    public Observable<LearnRecord> getLearnRecord(int catalogId) {
        return catalogDataStore.getLearnRecord(catalogId).map(learnRecordEntityDataMapper::transform);
    }

    @Override
    public Observable<Integer> createLearnRecord(int catalogId, Map<String, Object> learnRecord) {
        return catalogDataStore.createLearnRecord(catalogId,learnRecord);
    }

    @Override
    public Observable<Void> updateLearnRecord(int catalogId, Map<String, Object> learnRecord) {
        return catalogDataStore.updateLearnRecord(catalogId,learnRecord);
    }

    @Override
    public Observable<Integer> getDocumentInCatalog(int catalogId, Map<String, Object> document) {
        return catalogDataStore.getDocumentInCatalog(catalogId,document);
    }

    @Override
    public Observable<DocumentList> getDocumentsInCatalog(int catalogId, Integer start, Integer count, String sort) {
        return catalogDataStore.getDocumentsInCatalog(catalogId,start,count,sort).map(documentListEntityDataMapper::transform);
    }

    @Override
    public Observable<Answer> getAnswer(int catalogId) {
        return catalogDataStore.getAnswer(catalogId).map(answerEntityDataMapper::transform);
    }

    @Override
    public Observable<Integer> createAnser(int catalogId, Map<String, Object> answer) {
        return catalogDataStore.createAnser(catalogId, answer);
    }
}
