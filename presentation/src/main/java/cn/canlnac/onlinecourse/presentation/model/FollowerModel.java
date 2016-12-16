package cn.canlnac.onlinecourse.presentation.model;

import java.util.List;

/**
 * 关注数据模型.
 */

public class FollowerModel {
    private int total;

    private List<LoginModel> users;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LoginModel> getUsers() {
        return users;
    }

    public void setUsers(List<LoginModel> users) {
        this.users = users;
    }
}
