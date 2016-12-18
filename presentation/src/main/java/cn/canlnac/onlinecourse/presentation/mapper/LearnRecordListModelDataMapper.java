package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.LearnRecordList;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.LearnRecordListModel;

@PerActivity
public class LearnRecordListModelDataMapper {
    private final LearnRecordModelDataMapper learnRecordModelDataMapper;
    @Inject
    public LearnRecordListModelDataMapper(LearnRecordModelDataMapper learnRecordModelDataMapper) {
        this.learnRecordModelDataMapper = learnRecordModelDataMapper;
    }

    public LearnRecordListModel transform(LearnRecordList learnRecordList) {
        if (learnRecordList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        LearnRecordListModel learnRecordListModel = new LearnRecordListModel();
        learnRecordListModel.setTotal(learnRecordList.getTotal());
        learnRecordListModel.setLearnRecords(learnRecordModelDataMapper.transform(learnRecordList.getLearnRecords()));

        return learnRecordListModel;
    }
}
