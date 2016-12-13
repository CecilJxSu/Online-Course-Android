package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.CourseEntity;
import cn.canlnac.onlinecourse.domain.Course;

/**
 * 课程实体类转换.
 */
@Singleton
public class CourseEntityDataMapper {
    private LoginEntityDataMapper loginEntityDataMapper;
    @Inject
    public CourseEntityDataMapper(LoginEntityDataMapper loginEntityDataMapper) {
        this.loginEntityDataMapper = loginEntityDataMapper;
    }

    /**
     * 转换
     * @param courseEntity 课程实体类
     * @return
     */
    public Course transform(CourseEntity courseEntity) {
        Course course = null;
        if (courseEntity != null) {
            course = new Course();
            course.setId(courseEntity.getId());
            course.setAuthor(loginEntityDataMapper.transform(courseEntity.getAuthor()));
            course.setDate(courseEntity.getDate());
            course.setCommentCount(courseEntity.getCommentCount());
            course.setDepartment(courseEntity.getDepartment());
            course.setFavorite(courseEntity.isFavorite());
            course.setFavoriteCount(courseEntity.getFavoriteCount());
            course.setIntroduction(courseEntity.getIntroduction());
            course.setLike(courseEntity.isLike());
            course.setLikeCount(courseEntity.getLikeCount());
            course.setName(courseEntity.getName());
        }
        return course;
    }

    /**
     * 转换列表
     * @param courseEntityList    课程实体类列表
     * @return
     */
    public List<Course> transform(List<CourseEntity> courseEntityList) {
        List<Course> courseList = new ArrayList<>(courseEntityList.size());
        Course course;
        for (CourseEntity courseEntity : courseEntityList) {
            course = transform(courseEntity);
            if (course != null) {
                courseList.add(course);
            }
        }

        return courseList;
    }
}
