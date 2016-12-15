package cn.canlnac.onlinecourse.data.repository.datasource;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.CatalogEntity;
import cn.canlnac.onlinecourse.data.entity.CommentListEntity;
import cn.canlnac.onlinecourse.data.entity.CourseEntity;
import cn.canlnac.onlinecourse.data.entity.CourseListEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentListEntity;
import rx.Observable;

/**
 * 课程数据储存.
 */

public interface CourseDataStore {
    /** 获取指定课程 */
    Observable<CourseEntity> getCourse(int courseId);
    /** 修改课程 */
    Observable<Void> updateCourse(int courseId, Map<String,String> course);
    /** 删除课程 */
    Observable<Void> deleteCourse(int courseId);
    /** 创建课程 */
    Observable<Integer> createCourse(Map<String,String> course);
    /** 点赞课程 */
    Observable<Void> likeCourse(int courseId);
    /** 取消点赞 */
    Observable<Void> unlikeCourse(int courseId);
    /** 收藏课程 */
    Observable<Void> favoriteCourse(int courseId);
    /** 取消收藏 */
    Observable<Void> unfavoriteCourse(int courseId);
    /** 创建文档 */
    Observable<Integer> createDocument(int courseId, Map<String,String> document);
    /** 课程下的文档 */
    Observable<DocumentListEntity> getDocumentsInCourse(int courseId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort);
    /** 获取课程下的评论 */
    Observable<CommentListEntity> getCommentsInCourse(int courseId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort);
    /** 创建评论 */
    Observable<Integer> createCommentInCourse(int courseId, Map<String,Object> comment);
    /** 回复评论 */
    Observable<Integer> replyCommentInCourse(int courseId, int commentId, Map<String,Object> reply);
    /** 创建目录 */
    Observable<Integer> createCatalog(int courseId, Map<String,Object> catalog);
    /** 获取课程下的章节 */
    Observable<List<CatalogEntity>> getCatalogs(int courseId);

    /** 获取课程列表 */
    Observable<CourseListEntity> getCourses(@Nullable Integer start, @Nullable Integer count, String sort, List<String> departments);
}
