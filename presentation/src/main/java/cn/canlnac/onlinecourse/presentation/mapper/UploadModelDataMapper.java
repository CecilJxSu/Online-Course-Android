package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Upload;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.UploadModel;

@PerActivity
public class UploadModelDataMapper {
    @Inject
    public UploadModelDataMapper() {}

    public UploadModel transform(Upload upload) {
        if (upload == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        UploadModel uploadModel = new UploadModel();
        uploadModel.setFileSize(upload.getFileSize());
        uploadModel.setFileType(upload.getFileType());
        uploadModel.setFileUrl(upload.getFileUrl());

        return uploadModel;
    }

    public List<UploadModel> transform(List<Upload> uploadList) {
        List<UploadModel> uploadModelList = new ArrayList<>(uploadList.size());
        UploadModel uploadModel;
        for (Upload upload : uploadList) {
            uploadModel = transform(upload);
            if (upload != null) {
                uploadModelList.add(uploadModel);
            }
        }

        return uploadModelList;
    }
}
