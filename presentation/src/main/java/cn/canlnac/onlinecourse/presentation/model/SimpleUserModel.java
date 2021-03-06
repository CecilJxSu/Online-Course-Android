package cn.canlnac.onlinecourse.presentation.model;

/**
 * 用户基本信息数据模型.
 */

public class SimpleUserModel {
    private int id;

    private String name;

    private String iconUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
