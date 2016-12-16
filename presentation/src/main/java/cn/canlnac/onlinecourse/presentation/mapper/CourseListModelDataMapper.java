package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.CourseList;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.CourseListModel;

@PerActivity
public class CourseListModelDataMapper {
    private final CourseModelDataMapper courseModelDataMapper;
    @Inject
    public CourseListModelDataMapper(CourseModelDataMapper courseModelDataMapper) {
        this.courseModelDataMapper = courseModelDataMapper;
    }

    public CourseListModel transform(CourseList courseList) {
        if (courseList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        CourseListModel courseListModel = new CourseListModel();
        courseListModel.setTotal(courseList.getTotal());

        return courseListModel;
    }
}
