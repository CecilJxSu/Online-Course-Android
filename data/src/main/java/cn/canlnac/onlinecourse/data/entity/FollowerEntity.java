package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 关注.
 */

public class FollowerEntity {
    @SerializedName("total")
    private int total;

    @SerializedName("users")
    private List<LoginEntity> users;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LoginEntity> getUsers() {
        return users;
    }

    public void setUsers(List<LoginEntity> users) {
        this.users = users;
    }
}
