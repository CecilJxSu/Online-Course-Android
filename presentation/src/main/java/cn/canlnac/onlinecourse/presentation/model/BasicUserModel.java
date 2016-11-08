package cn.canlnac.onlinecourse.presentation.model;

/**
 * 用户基本信息模型.
 */

public class BasicUserModel {
    private final int id;
    private String name;
    private String iconUrl;

    public BasicUserModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** BasicUser Details *****\n");
        stringBuilder.append("id=" + this.getId() + "\n");
        stringBuilder.append("name=" + this.getName() + "\n");
        stringBuilder.append("iconUrl=" + this.getIconUrl() + "\n");
        stringBuilder.append("*****************************\n");

        return stringBuilder.toString();
    }
}
