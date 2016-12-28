package cn.canlnac.onlinecourse.data.repository.datasource;

import java.io.File;
import java.util.List;

import cn.canlnac.onlinecourse.data.entity.UploadEntity;
import rx.Observable;

/**
 * 上传文件数据储存.
 */

public interface UploadDataStore {
    /** 上传文件 */
    Observable<List<UploadEntity>> uploadFiles(List<File> files);
}
