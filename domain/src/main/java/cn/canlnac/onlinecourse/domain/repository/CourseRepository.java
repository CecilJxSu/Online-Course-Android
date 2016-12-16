package cn.canlnac.onlinecourse.domain.repository;

import java.util.List;
import java.util.Map;

import cn.canlnac.onlinecourse.domain.Catalog;
import cn.canlnac.onlinecourse.domain.CommentList;
import cn.canlnac.onlinecourse.domain.Course;
import cn.canlnac.onlinecourse.domain.CourseList;
import cn.canlnac.onlinecourse.domain.DocumentList;
import rx.Observable;

/**
 * 课程接口.
 */
public interface CourseRepository {
    /** 获取指定课程 */
    Observable<Course> getCourse(int courseId);
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
    Observable<DocumentList> getDocumentsInCourse(int courseId, Integer start, Integer count, String sort);
    /** 获取课程下的评论 */
    Observable<CommentList> getCommentsInCourse(int courseId, Integer start, Integer count, String sort);
    /** 创建评论 */
    Observable<Integer> createCommentInCourse(int courseId, Map<String,Object> comment);
    /** 回复评论 */
    Observable<Integer> replyCommentInCourse(int courseId, int commentId, Map<String,Object> reply);
    /** 创建目录 */
    Observable<Integer> createCatalog(int courseId, Map<String,Object> catalog);
    /** 获取课程下的章节 */
    Observable<List<Catalog>> getCatalogs(int courseId);

    /** 获取课程列表 */
    Observable<CourseList> getCourses(Integer start, Integer count, String sort, List<String> departments);
}
