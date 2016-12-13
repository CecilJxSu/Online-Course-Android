package cn.canlnac.onlinecourse.domain;

import java.util.List;

/**
 * 关注.
 */

public class Follower {
    private int total;

    private List<Login> users;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Login> getUsers() {
        return users;
    }

    public void setUsers(List<Login> users) {
        this.users = users;
    }
}
