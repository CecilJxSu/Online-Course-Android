package cn.canlnac.onlinecourse.domain;

/**
 * 上传文件.
 */

public class Upload {
    private String fileUrl;

    private String fileType;

    private float fileSize;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
