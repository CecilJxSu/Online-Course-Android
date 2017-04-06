package cn.canlnac.onlinecourse.domain.repository;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.Answer;
import cn.canlnac.onlinecourse.domain.Catalog;
import cn.canlnac.onlinecourse.domain.DocumentList;
import cn.canlnac.onlinecourse.domain.LearnRecord;
import cn.canlnac.onlinecourse.domain.QuestionList;
import rx.Observable;

/**
 * 目录接口.
 */
public interface CatalogRepository {
    /** 获取目录 */
    Observable<Catalog> getCatalog(int catalogId);
    /** 修改目录 */
    Observable<Void> updateCatalog(int catalogId, Map<String,Object> catalog);
    /** 删除目录 */
    Observable<Void> deleteCatalog(int catalogId);
    /** 创建小测 */
    Observable<Integer> createQuestion(int catalogId, Map<String,Object> question);
    /** 更新小测 */
    Observable<Void> updateQuestion(int catalogId, Map<String,Object> question);
    /** 删除小测 */
    Observable<Void> deleteQuestion(int catalogId);
    /** 获取小测 */
    Observable<QuestionList> getQuestion(int catalogId);
    /** 获取学习记录 */
    Observable<LearnRecord> getLearnRecord(int catalogId);
    /** 创建学习记录 */
    Observable<Integer> createLearnRecord(int catalogId, Map<String,Object> learnRecord);
    /** 更新学习记录 */
    Observable<Void> updateLearnRecord(int catalogId, Map<String,Object> learnRecord);
    /** 创建文档 */
    Observable<Integer> createDocumentInCatalog(int catalogId, Map<String,Object> document);
    /** 文档列表 */
    Observable<DocumentList> getDocumentsInCatalog(int catalogId, Integer start, Integer count, String sort);
    /** 获取章节下的回答 */
    Observable<Answer> getAnswer(int catalogId);
    /** 创建回答 */
    Observable<Integer> createAnser(int catalogId, Map<String,Object> answer);
}
