package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 登录.
 */

public class LoginEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("userStatus")
    private String userStatus;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("gender")
    private String gender;

    @SerializedName("iconUrl")
    private String iconUrl;

    @SerializedName("lockDate")
    private long lockDate;

    @SerializedName("lockEndDate")
    private long lockEndDate;

    @SerializedName("jwt")
    private String jwt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public long getLockDate() {
        return lockDate;
    }

    public void setLockDate(long lockDate) {
        this.lockDate = lockDate;
    }

    public long getLockEndDate() {
        return lockEndDate;
    }

    public void setLockEndDate(long lockEndDate) {
        this.lockEndDate = lockEndDate;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
