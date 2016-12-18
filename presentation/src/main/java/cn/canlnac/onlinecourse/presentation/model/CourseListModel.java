package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 课程列表数据模型.
 */

public class CourseListModel {
    private int total;

    private List<CourseModel> courses;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CourseModel> getCourseModels() {
        return courses;
    }

    public void setCourseModels(List<CourseModel> courses) {
        this.courses = courses;
    }
}
