package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 小测.
 */

public class Paper {
    private String type;

    private List<Question> questions;

    private float score;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
