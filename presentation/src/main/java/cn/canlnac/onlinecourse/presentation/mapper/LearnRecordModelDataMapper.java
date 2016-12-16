package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.LearnRecord;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.LearnRecordModel;

@PerActivity
public class LearnRecordModelDataMapper {
    private final LoginModelDataMapper loginModelDataMapper;
    private final CatalogModelDataMapper catalogModelDataMapper;

    @Inject
    public LearnRecordModelDataMapper(LoginModelDataMapper loginModelDataMapper,CatalogModelDataMapper catalogModelDataMapper) {
        this.loginModelDataMapper = loginModelDataMapper;
        this.catalogModelDataMapper = catalogModelDataMapper;
    }

    public LearnRecordModel transform(LearnRecord learnRecord) {
        if (learnRecord == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        LearnRecordModel learnRecordModel = new LearnRecordModel();
        learnRecordModel.setCatalogModel(catalogModelDataMapper.transform(learnRecord.getCatalog()));
        learnRecordModel.setDate(learnRecord.getDate());
        learnRecordModel.setDate(learnRecord.getDate());
        learnRecordModel.setId(learnRecord.getId());
        learnRecordModel.setLastDate(learnRecord.getLastDate());
        learnRecordModel.setProgress(learnRecord.getProgress());
        learnRecordModel.setUser(loginModelDataMapper.transform(learnRecord.getUser()));

        return learnRecordModel;
    }

    public List<LearnRecordModel> transform(List<LearnRecord> learnRecordList) {
        List<LearnRecordModel> learnRecordModelList = new ArrayList<>(learnRecordList.size());
        LearnRecordModel learnRecordModel;
        for (LearnRecord learnRecord : learnRecordList) {
            learnRecordModel = transform(learnRecord);
            if (learnRecord != null) {
                learnRecordModelList.add(learnRecordModel);
            }
        }

        return learnRecordModelList;
    }
}
