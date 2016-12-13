package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.CourseListEntity;
import cn.canlnac.onlinecourse.domain.CourseList;

/**
 * 课程列表实体类转换.
 */
@Singleton
public class CourseListEntityDataMapper {
    private CourseEntityDataMapper courseEntityDataMapper;
    @Inject
    public CourseListEntityDataMapper(CourseEntityDataMapper courseEntityDataMapper) {
        this.courseEntityDataMapper = courseEntityDataMapper;
    }

    /**
     * 转换
     * @param courseListEntity 课程列表实体类
     * @return
     */
    public CourseList transform(CourseListEntity courseListEntity) {
        CourseList courseList = null;
        if (courseListEntity != null) {
            courseList = new CourseList();
            courseList.setTotal(courseListEntity.getTotal());
            courseList.setCourses(courseEntityDataMapper.transform(courseListEntity.getCourses()));
        }
        return courseList;
    }
}
