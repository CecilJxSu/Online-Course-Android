package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * 小测.
 */

public class QuestionEntity {
    @SerializedName("index")
    private int index;

    @SerializedName("type")
    private String type;

    @SerializedName("question")
    private String question;

    @SerializedName("answer")
    private List<String> answer;

    @SerializedName("item")
    private Map<String,String> item;

    @SerializedName("explains")
    private String explains;

    @SerializedName("url")
    private String url;

    @SerializedName("score")
    private float score;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public Map<String, String> getItem() {
        return item;
    }

    public void setItem(Map<String, String> item) {
        this.item = item;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
