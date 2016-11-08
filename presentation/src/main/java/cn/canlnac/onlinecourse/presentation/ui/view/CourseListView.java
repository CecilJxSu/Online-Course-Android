package cn.canlnac.onlinecourse.presentation.ui.view;

import java.util.Collection;

import cn.canlnac.onlinecourse.presentation.model.CourseModel;

/**
 * 加载课程列表视图.
 */

public interface CourseListView extends LoadDataView {
    /**
     * 在UI中渲染课程列表
     * @param courseModels
     */
    void renderCourseList(Collection<CourseModel> courseModels);

    /**
     * 查看一个 {@link CourseModel} 详情
     * @param courseModel   课程模型
     */
    void viewCourse(CourseModel courseModel);
}
