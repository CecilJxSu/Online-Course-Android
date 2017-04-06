package cn.canlnac.onlinecourse.presentation.model;

import java.util.Map;

/**
 * 回答数据模型.
 */

public class AnswerModel {
    private int id;

    private long date;

    private int questionId;

    private LoginModel user;

    private Map<Integer,String> answer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public LoginModel getUser() {
        return user;
    }

    public void setUser(LoginModel user) {
        this.user = user;
    }

    public Map<Integer, String> getAnswer() {
        return answer;
    }

    public void setAnswer(Map<Integer, String> answer) {
        this.answer = answer;
    }
}
