package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 用户实体类.
 */

public class UserEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private long date;

    @SerializedName("status")
    private String status;

    @SerializedName("user_status")
    private String userStatus;

    @SerializedName("lock_date")
    private long lockDate;

    @SerializedName("lock_end_date")
    private long lockEndDate;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public long getLockDate() {
        return lockDate;
    }

    public void setLockDate(long locklong) {
        this.lockDate = lockDate;
    }

    public long getLockEndDate() {
        return lockEndDate;
    }

    public void setLockEndDate(long lockEndDate) {
        this.lockEndDate = lockEndDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
