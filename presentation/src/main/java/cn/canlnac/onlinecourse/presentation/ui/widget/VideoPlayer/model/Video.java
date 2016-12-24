package cn.canlnac.onlinecourse.presentation.ui.widget.VideoPlayer.model;

/**
 * 视频对象
 */
public class Video {
    private String mVideoName;  //视频名称
    private String mPlayUrl;    //视频地址

    public String getVideoName() {
        return mVideoName;
    }

    public void setVideoName(String videoName) {
        mVideoName = videoName;
    }

    public String getPlayUrl() {
        return mPlayUrl;
    }

    public void setPlayUrl(String playUrl) {
        mPlayUrl = playUrl;
    }
}
