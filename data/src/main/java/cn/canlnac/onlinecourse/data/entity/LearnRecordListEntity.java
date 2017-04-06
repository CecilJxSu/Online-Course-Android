package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 学习记录列表.
 */

public class LearnRecordListEntity {
    @SerializedName("total")
    private int total;

    @SerializedName("learnRecords")
    private List<LearnRecordEntity> learnRecords;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LearnRecordEntity> getLearnRecords() {
        return learnRecords;
    }

    public void setLearnRecords(List<LearnRecordEntity> learnRecords) {
        this.learnRecords = learnRecords;
    }
}
