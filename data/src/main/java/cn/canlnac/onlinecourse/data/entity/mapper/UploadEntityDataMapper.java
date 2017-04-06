package cn.canlnac.onlinecourse.data.entity.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.UploadEntity;
import cn.canlnac.onlinecourse.domain.Upload;

/**
 * 上传文件类转换.
 */
@Singleton
public class UploadEntityDataMapper {

    @Inject
    public UploadEntityDataMapper() {}

    /**
     * 转换
     * @param uploadEntity 上传文件类
     * @return
     */
    public Upload transform(UploadEntity uploadEntity) {
        Upload upload = null;
        if (uploadEntity != null) {
            upload = new Upload();
            upload.setFileUrl(uploadEntity.getFileName());
            upload.setFileSize(uploadEntity.getFileSize());
            upload.setFileType(uploadEntity.getFileType());
        }
        return upload;
    }

    /**
     * 转换列表
     * @param uploadEntityList    上传文件实体类列表
     * @return
     */
    public List<Upload> transform(List<UploadEntity> uploadEntityList) {
        List<Upload> uploadList = new ArrayList<>(uploadEntityList.size());
        Upload upload;
        for (UploadEntity uploadEntity : uploadEntityList) {
            upload = transform(uploadEntity);
            if (upload != null) {
                uploadList.add(upload);
            }
        }

        return uploadList;
    }
}
