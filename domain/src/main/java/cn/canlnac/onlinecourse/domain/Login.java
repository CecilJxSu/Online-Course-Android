package cn.canlnac.onlinecourse.domain;

/**
 * 登录类
 */

public class Login {
    private int id;

    private String userStatus;

    private String nickname;

    private String gender;

    private String iconUrl;

    private long lockDate;

    private long lockEndDate;

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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** Login Entity Details *****\n");
        stringBuilder.append("id=" + this.getId() + "\n");
        stringBuilder.append("userStatus=" + this.getUserStatus() + "\n");
        stringBuilder.append("nickname=" + this.getNickname() + "\n");
        stringBuilder.append("gender=" + this.getGender() + "\n");
        stringBuilder.append("iconUrl=" + this.getIconUrl() + "\n");
        stringBuilder.append("lockDate=" + this.getLockDate() + "\n");
        stringBuilder.append("lockEndDate=" + this.getLockEndDate() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }
}
