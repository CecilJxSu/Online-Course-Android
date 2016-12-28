package cn.canlnac.onlinecourse.data.repository;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.UploadEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.UploadDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.UploadDataStoreFactory;
import cn.canlnac.onlinecourse.domain.Upload;
import cn.canlnac.onlinecourse.domain.repository.UploadRepository;
import rx.Observable;

/**
 * 上传数据接口，提供给domain调用.
 */
@Singleton
public class UploadDataRepository implements UploadRepository {
    private final UploadDataStore uploadDataStore;

    private final UploadEntityDataMapper uploadEntityDataMapper;

    @Inject
    public UploadDataRepository(
            UploadDataStoreFactory uploadDataStoreFactory,
            UploadEntityDataMapper uploadEntityDataMapper
    ) {
        this.uploadDataStore = uploadDataStoreFactory.create();
        this.uploadEntityDataMapper = uploadEntityDataMapper;
    }

    @Override
    public Observable<List<Upload>> uploadFiles(List<File> files) {
        return uploadDataStore.uploadFiles(files).map(uploadEntityDataMapper::transform);
    }

}
