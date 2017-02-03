package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 小测列表.
 */

public class QuestionList {
    private int total;

    private List<Question> questions;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
