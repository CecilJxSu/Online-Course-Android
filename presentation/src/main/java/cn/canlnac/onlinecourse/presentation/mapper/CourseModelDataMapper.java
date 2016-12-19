package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Course;
import cn.canlnac.onlinecourse.domain.Login;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;

@PerActivity
public class CourseModelDataMapper {
    private final LoginModelDataMapper loginModelDataMapper;
    @Inject
    public CourseModelDataMapper(LoginModelDataMapper loginModelDataMapper) {
        this.loginModelDataMapper = loginModelDataMapper;
    }

    public CourseModel transform(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CourseModel courseModel = new CourseModel();
        courseModel.setFavorite(course.isFavorite());
        courseModel.setLike(course.isLike());

        if (course.getAuthor() == null) {
            course.setAuthor(new Login());
        }

        courseModel.setAuthor(loginModelDataMapper.transform(course.getAuthor()));
        courseModel.setId(course.getId());
        courseModel.setFavoriteCount(course.getFavoriteCount());
        courseModel.setCommentCount(course.getCommentCount());
        courseModel.setDate(course.getDate());
        courseModel.setDepartment(course.getDepartment());
        courseModel.setIntroduction(course.getIntroduction());
        courseModel.setName(course.getName());
        courseModel.setLikeCount(course.getLikeCount());
        courseModel.setWatchCount(course.getWatchCount());

        return courseModel;
    }

    public List<CourseModel> transform(List<Course> courseList) {
        List<CourseModel> courseModelList = new ArrayList<>(courseList.size());
        CourseModel courseModel;
        for (Course course : courseList) {
            courseModel = transform(course);
            if (course != null) {
                courseModelList.add(courseModel);
            }
        }

        return courseModelList;
    }
}
