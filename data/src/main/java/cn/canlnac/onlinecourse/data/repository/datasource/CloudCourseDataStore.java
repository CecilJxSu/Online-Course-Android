package cn.canlnac.onlinecourse.data.repository.datasource;

import android.support.annotation.Nullable;

import java.util.List;
import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.CatalogEntity;
import cn.canlnac.onlinecourse.data.entity.CommentListEntity;
import cn.canlnac.onlinecourse.data.entity.CourseEntity;
import cn.canlnac.onlinecourse.data.entity.CourseListEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentListEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;
import rx.Observable;

/**
 * 从服务器中获取课程数据.
 */

public class CloudCourseDataStore implements CourseDataStore {
    private final RestApi restApi;

    public CloudCourseDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<CourseEntity> getCourse(int courseId) {
        return this.restApi.getCourse(courseId);
    }

    @Override
    public Observable<Void> updateCourse(int courseId, Map<String, String> course) {
        return this.restApi.updateCourse(courseId, course);
    }

    @Override
    public Observable<Void> deleteCourse(int courseId) {
        return this.restApi.deleteCourse(courseId);
    }

    @Override
    public Observable<Integer> createCourse(Map<String, String> course) {
        return this.restApi.createCourse(course);
    }

    @Override
    public Observable<Void> likeCourse(int courseId) {
        return this.restApi.likeCourse(courseId);
    }

    @Override
    public Observable<Void> unlikeCourse(int courseId) {
        return this.restApi.unlikeCourse(courseId);
    }

    @Override
    public Observable<Void> favoriteCourse(int courseId) {
        return this.restApi.favoriteCourse(courseId);
    }

    @Override
    public Observable<Void> unfavoriteCourse(int courseId) {
        return this.restApi.unfavoriteCourse(courseId);
    }

    @Override
    public Observable<Integer> createDocument(int courseId, Map<String, String> document) {
        return this.restApi.createDocument(courseId,document);
    }

    @Override
    public Observable<DocumentListEntity> getDocumentsInCourse(int courseId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return this.restApi.getDocumentsInCourse(courseId,start,count,sort);
    }

    @Override
    public Observable<CommentListEntity> getCommentsInCourse(int courseId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return this.restApi.getCommentsInCourse(courseId,start,count,sort);
    }

    @Override
    public Observable<Integer> createCommentInCourse(int courseId, Map<String, Object> comment) {
        return this.restApi.createCommentInCourse(courseId, comment);
    }

    @Override
    public Observable<Integer> replyCommentInCourse(int courseId, int commentId, Map<String, Object> reply) {
        return this.restApi.replyCommentInCourse(courseId, commentId, reply);
    }

    @Override
    public Observable<Integer> createCatalog(int courseId, Map<String, Object> catalog) {
        return this.restApi.createCatalog(courseId,catalog);
    }

    @Override
    public Observable<List<CatalogEntity>> getCatalogs(int courseId) {
        return this.restApi.getCatalogs(courseId);
    }

    @Override
    public Observable<CourseListEntity> getCourses(@Nullable Integer start, @Nullable Integer count, String sort, List<String> departments) {
        return this.restApi.getCourses(start,count,sort,departments);
    }
}
