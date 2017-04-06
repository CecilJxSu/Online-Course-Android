package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 课程列表.
 */

public class CourseList {
    private int total;

    private List<Course> courses;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
