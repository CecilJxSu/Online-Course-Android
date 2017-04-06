package cn.canlnac.onlinecourse.domain;

/**
 * 用户实体类.
 */

public class User {
    private int id;

    private long date;

    private String status;

    private String userStatus;

    private long lockDate;

    private long lockEndDate;

    private String username;

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

    public void setLockDate(long lockDate) {
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
