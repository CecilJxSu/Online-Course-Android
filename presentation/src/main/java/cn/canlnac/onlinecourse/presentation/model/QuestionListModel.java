package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 小测列表数据模型.
 */

public class QuestionListModel {
    private int total;

    private List<PaperModel> questions;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PaperModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<PaperModel> questions) {
        this.questions = questions;
    }
}
