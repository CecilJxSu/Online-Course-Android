package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 学习记录数据模型.
 */

public class LearnRecordListModel {
    private int total;

    private List<LearnRecordModel> learnRecords;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LearnRecordModel> getLearnRecords() {
        return learnRecords;
    }

    public void setLearnRecords(List<LearnRecordModel> learnRecords) {
        this.learnRecords = learnRecords;
    }
}
