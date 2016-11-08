package cn.canlnac.onlinecourse.domain.repository;

import java.util.List;

import cn.canlnac.onlinecourse.domain.Course;
import rx.Observable;

/**
 * 课程仓库.
 */

public interface CourseRepository {
    Observable<List<Course>> courses();
}
