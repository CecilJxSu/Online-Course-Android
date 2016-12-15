package cn.canlnac.onlinecourse.data.repository;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.CatalogEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.CommentListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.CourseEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.CourseListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.DocumentListEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.CourseDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.CourseDataStoreFactory;
import cn.canlnac.onlinecourse.domain.Catalog;
import cn.canlnac.onlinecourse.domain.CommentList;
import cn.canlnac.onlinecourse.domain.Course;
import cn.canlnac.onlinecourse.domain.CourseList;
import cn.canlnac.onlinecourse.domain.DocumentList;
import cn.canlnac.onlinecourse.domain.repository.CourseRepository;
import rx.Observable;

/**
 * 课程数据接口，提供给domain调用.
 */
@Singleton
public class CourseDataRepository implements CourseRepository {
    private final CourseDataStore courseDataStore;

    private final CourseEntityDataMapper courseEntityDataMapper;
    private final DocumentListEntityDataMapper documentListEntityDataMapper;
    private final CommentListEntityDataMapper commentListEntityDataMapper;
    private final CatalogEntityDataMapper catalogEntityDataMapper;
    private final CourseListEntityDataMapper courseListEntityDataMapper;

    @Inject
    public CourseDataRepository(
            CourseDataStoreFactory courseDataStoreFactory,
            CourseEntityDataMapper courseEntityDataMapper,
            DocumentListEntityDataMapper documentListEntityDataMapper,
            CommentListEntityDataMapper commentListEntityDataMapper,
            CatalogEntityDataMapper catalogEntityDataMapper,
            CourseListEntityDataMapper courseListEntityDataMapper
    ) {
        this.courseDataStore = courseDataStoreFactory.create();
        this.courseEntityDataMapper = courseEntityDataMapper;
        this.documentListEntityDataMapper = documentListEntityDataMapper;
        this.commentListEntityDataMapper = commentListEntityDataMapper;
        this.catalogEntityDataMapper = catalogEntityDataMapper;
        this.courseListEntityDataMapper = courseListEntityDataMapper;
    }

    @Override
    public Observable<Course> getCourse(int courseId) {
        return courseDataStore.getCourse(courseId).map(courseEntityDataMapper::transform);
    }

    @Override
    public Observable<Void> updateCourse(int courseId, Map<String, String> course) {
        return courseDataStore.updateCourse(courseId, course);
    }

    @Override
    public Observable<Void> deleteCourse(int courseId) {
        return courseDataStore.deleteCourse(courseId);
    }

    @Override
    public Observable<Integer> createCourse(Map<String, String> course) {
        return courseDataStore.createCourse(course);
    }

    @Override
    public Observable<Void> likeCourse(int courseId) {
        return courseDataStore.likeCourse(courseId);
    }

    @Override
    public Observable<Void> unlikeCourse(int courseId) {
        return courseDataStore.unlikeCourse(courseId);
    }

    @Override
    public Observable<Void> favoriteCourse(int courseId) {
        return courseDataStore.favoriteCourse(courseId);
    }

    @Override
    public Observable<Void> unfavoriteCourse(int courseId) {
        return courseDataStore.unfavoriteCourse(courseId);
    }

    @Override
    public Observable<Integer> createDocument(int courseId, Map<String, String> document) {
        return courseDataStore.createDocument(courseId, document);
    }

    @Override
    public Observable<DocumentList> getDocumentsInCourse(int courseId, Integer start, Integer count, String sort) {
        return courseDataStore.getDocumentsInCourse(courseId, start, count, sort).map(documentListEntityDataMapper::transform);
    }

    @Override
    public Observable<CommentList> getCommentsInCourse(int courseId, Integer start, Integer count, String sort) {
        return courseDataStore.getCommentsInCourse(courseId,start,count,sort).map(commentListEntityDataMapper::transform);
    }

    @Override
    public Observable<Integer> createCommentInCourse(int courseId, Map<String, Object> comment) {
        return courseDataStore.createCommentInCourse(courseId,comment);
    }

    @Override
    public Observable<Integer> replyCommentInCourse(int courseId, int commentId, Map<String, Object> reply) {
        return courseDataStore.replyCommentInCourse(courseId, commentId, reply);
    }

    @Override
    public Observable<Integer> createCatalog(int courseId, Map<String, Object> catalog) {
        return courseDataStore.createCatalog(courseId, catalog);
    }

    @Override
    public Observable<List<Catalog>> getCatalogs(int courseId) {
        return courseDataStore.getCatalogs(courseId).map(catalogEntityDataMapper::transform);
    }

    @Override
    public Observable<CourseList> getCourses(Integer start, Integer count, String sort, List<String> departments) {
        return courseDataStore.getCourses(start, count, sort, departments).map(courseListEntityDataMapper::transform);
    }
}
