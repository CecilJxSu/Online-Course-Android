package cn.canlnac.onlinecourse.domain.repository;

import java.io.File;
import java.util.List;

import cn.canlnac.onlinecourse.domain.Upload;
import rx.Observable;

/**
 * 上传文件接口.
 */
public interface UploadRepository {
    /** 上传文件 */
    Observable<List<Upload>> uploadFiles(List<File> files);

    /** 下载文件 */
    Observable<File> download(String fileUrl, File targetFile);
}
