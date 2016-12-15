package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 学习记录列表.
 */

public class LearnRecordList {
    private int total;

    private List<LearnRecord> learnRecords;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LearnRecord> getLearnRecords() {
        return learnRecords;
    }

    public void setLearnRecords(List<LearnRecord> learnRecords) {
        this.learnRecords = learnRecords;
    }
}
