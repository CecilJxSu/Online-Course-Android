package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 小测.
 */

public class PaperEntity {
    @SerializedName("type")
    private String type;

    @SerializedName("questions")
    private List<QuestionEntity> questions;

    @SerializedName("score")
    private float score;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
