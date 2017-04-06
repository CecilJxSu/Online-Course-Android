package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.LearnRecordListEntity;
import cn.canlnac.onlinecourse.domain.LearnRecordList;

/**
 * 学习记录列表实体类转换.
 */
@Singleton
public class LearnRecordListEntityDataMapper {
    private LearnRecordEntityDataMapper learnRecordEntityDataMapper;

    @Inject
    public LearnRecordListEntityDataMapper(LearnRecordEntityDataMapper learnRecordEntityDataMapper) {
        this.learnRecordEntityDataMapper = learnRecordEntityDataMapper;
    }

    /**
     * 转换
     * @param learnRecordListEntity 学习记录列表实体类
     * @return
     */
    public LearnRecordList transform(LearnRecordListEntity learnRecordListEntity) {
        LearnRecordList learnRecordList = null;
        if (learnRecordListEntity != null) {
            learnRecordList = new LearnRecordList();
            learnRecordList.setTotal(learnRecordListEntity.getTotal());
            learnRecordList.setLearnRecords(learnRecordEntityDataMapper.transform(learnRecordListEntity.getLearnRecords()));
        }
        return learnRecordList;
    }
}
