package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 小测列表.
 */

public class QuestionListEntity {
    @SerializedName("total")
    private int total;

    @SerializedName("questions")
    private List<PaperEntity> questions;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PaperEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<PaperEntity> questions) {
        this.questions = questions;
    }
}
