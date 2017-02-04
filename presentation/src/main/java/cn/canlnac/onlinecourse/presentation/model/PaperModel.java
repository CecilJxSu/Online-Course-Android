package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 小测数据模型.
 */

public class PaperModel {
    private String type;

    private List<QuestionModel> questions;

    private float score;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
