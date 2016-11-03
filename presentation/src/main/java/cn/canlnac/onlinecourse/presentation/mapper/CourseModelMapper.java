package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Course;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;

/**
 * 课程转成课程模型.
 */
@PerActivity
public class CourseModelMapper {
    private final BasicUserModelMapper basicUserModelMapper;

    @Inject
    public CourseModelMapper(BasicUserModelMapper basicUserModelMapper) {
        this.basicUserModelMapper = basicUserModelMapper;
    }

    public CourseModel transform(Course course) {
        if (null == course) {
            throw new IllegalArgumentException("Cannot transform a null value to a CourseModel");
        }

        CourseModel courseModel = new CourseModel(course.getId());
        courseModel.setDate(course.getDate());
        courseModel.setName(course.getName());
        courseModel.setIntroduction(course.getIntroduction());
        courseModel.setAuthor(basicUserModelMapper.transform(course.getAuthor()));
        courseModel.setDepartment(course.getDepartment());
        courseModel.setDepartment(course.getDepartment());
        courseModel.setWatchCount(course.getWatchCount());
        courseModel.setLikeCount(course.getLikeCount());
        courseModel.setCommentCount(course.getCommentCount());
        courseModel.setFavoriteCount(course.getFavoriteCount());
        courseModel.setLike(course.isLike());
        courseModel.setFavorite(course.isFavorite());

        return courseModel;
    }

    public Collection<CourseModel> transform(Collection<Course> courses) {
        Collection<CourseModel> courseModels;

        if (null != courses && !courses.isEmpty()) {
            courseModels = new ArrayList<>();

            for (Course course : courses) {
                courseModels.add(transform(course));
            }
        } else {
            courseModels = Collections.emptyList();
        }

        return courseModels;
    }
}
