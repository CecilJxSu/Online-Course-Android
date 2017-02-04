package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 小测列表.
 */

public class QuestionList {
    private int total;

    private List<Paper> questions;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Paper> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Paper> questions) {
        this.questions = questions;
    }
}
