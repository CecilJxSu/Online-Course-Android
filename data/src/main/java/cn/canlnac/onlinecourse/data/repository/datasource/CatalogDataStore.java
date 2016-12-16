package cn.canlnac.onlinecourse.data.repository.datasource;

import android.support.annotation.Nullable;

import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.AnswerEntity;
import cn.canlnac.onlinecourse.data.entity.CatalogEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentListEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordEntity;
import cn.canlnac.onlinecourse.data.entity.QuestionEntity;
import rx.Observable;

/**
 * 目录数据储存.
 */

public interface CatalogDataStore {
    /** 获取目录 */
    Observable<CatalogEntity> getCatalog(int catalogId);
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
    Observable<QuestionEntity> getQuestion(int catalogId);
    /** 获取学习记录 */
    Observable<LearnRecordEntity> getLearnRecord(int catalogId);
    /** 创建学习记录 */
    Observable<Integer> createLearnRecord(int catalogId, Map<String,Object> learnRecord);
    /** 更新学习记录 */
    Observable<Void> updateLearnRecord(int catalogId, Map<String,Object> learnRecord);
    /** 创建文档 */
    Observable<Integer> createDocumentInCatalog(int catalogId, Map<String,Object> document);
    /** 文档列表 */
    Observable<DocumentListEntity> getDocumentsInCatalog(int catalogId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort);
    /** 获取章节下的回答 */
    Observable<AnswerEntity> getAnswer(int catalogId);
    /** 创建回答 */
    Observable<Integer> createAnser(int catalogId, Map<String,Object> answer);
}
