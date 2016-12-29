package cn.canlnac.onlinecourse.data.repository.datasource;

import java.io.File;
import java.util.List;

import cn.canlnac.onlinecourse.data.entity.UploadEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;
import rx.Observable;

/**
 * 上传文件.
 */

public class CloudUploadDataStore implements UploadDataStore {
    private final RestApi restApi;

    public CloudUploadDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<List<UploadEntity>> uploadFiles(List<File> files) {
        return this.restApi.uploadFiles(files);
    }
}
