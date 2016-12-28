package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 上传文件，返回数据.
 */

public class UploadEntity {
    @SerializedName("fileName")
    private String fileName;

    @SerializedName("fileType")
    private String fileType;

    @SerializedName("fileSize")
    private float fileSize;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public float getFileSize() {
        return fileSize;
    }

    public void setFileSize(float fileSize) {
        this.fileSize = fileSize;
    }
}
