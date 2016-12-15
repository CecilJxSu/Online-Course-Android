package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 学习记录ID.
 */

public class LearnRecordIdEntity {
    @SerializedName("learnRecordID")
    private int learnRecordId;

    public int getLearnRecordId() {
        return learnRecordId;
    }

    public void setLearnRecordId(int learnRecordId) {
        this.learnRecordId = learnRecordId;
    }
}
