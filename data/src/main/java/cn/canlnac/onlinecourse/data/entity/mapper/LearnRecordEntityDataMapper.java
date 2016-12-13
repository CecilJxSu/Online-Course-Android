package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.LearnRecordEntity;
import cn.canlnac.onlinecourse.domain.LearnRecord;

/**
 * 学习记录实体类转换.
 */
@Singleton
public class LearnRecordEntityDataMapper {
    private CatalogEntityDataMapper catalogEntityDataMapper;
    private LoginEntityDataMapper loginEntityDataMapper;

    @Inject
    public LearnRecordEntityDataMapper(CatalogEntityDataMapper catalogEntityDataMapper, LoginEntityDataMapper loginEntityDataMapper) {
        this.catalogEntityDataMapper = catalogEntityDataMapper;
        this.loginEntityDataMapper = loginEntityDataMapper;
    }

    /**
     * 转换
     * @param learnRecordEntity 学习记录实体类
     * @return
     */
    public LearnRecord transform(LearnRecordEntity learnRecordEntity) {
        LearnRecord learnRecord = null;
        if (learnRecordEntity != null) {
            learnRecord = new LearnRecord();
            learnRecord.setId(learnRecordEntity.getId());
            learnRecord.setCatalog(catalogEntityDataMapper.transform(learnRecordEntity.getCatalog()));
            learnRecord.setDate(learnRecordEntity.getDate());
            learnRecord.setLastDate(learnRecordEntity.getLastDate());
            learnRecord.setLastPosition(learnRecordEntity.getLastPosition());
            learnRecord.setProgress(learnRecordEntity.getProgress());
            learnRecord.setUser(loginEntityDataMapper.transform(learnRecordEntity.getUser()));
        }
        return learnRecord;
    }

    /**
     * 转换列表
     * @param learnRecordEntityList    学习记录实体类列表
     * @return
     */
    public List<LearnRecord> transform(List<LearnRecordEntity> learnRecordEntityList) {
        List<LearnRecord> learnRecordList = new ArrayList<>(learnRecordEntityList.size());
        LearnRecord learnRecord;
        for (LearnRecordEntity learnRecordEntity : learnRecordEntityList) {
            learnRecord = transform(learnRecordEntity);
            if (learnRecord != null) {
                learnRecordList.add(learnRecord);
            }
        }

        return learnRecordList;
    }
}
