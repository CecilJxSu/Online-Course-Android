package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 用户实体类.
 */

public class UserEntity {
    @SerializedName("id")
    private int id;

    @SerializedName("date")
    private Date date;

    @SerializedName("status")
    private String status;

    @SerializedName("user_status")
    private String userStatus;

    @SerializedName("lock_date")
    private Date lockDate;

    @SerializedName("lock_end_date")
    private Date lockEndDate;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Date getLockDate() {
        return lockDate;
    }

    public void setLockDate(Date lockDate) {
        this.lockDate = lockDate;
    }

    public Date getLockEndDate() {
        return lockEndDate;
    }

    public void setLockEndDate(Date lockEndDate) {
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
