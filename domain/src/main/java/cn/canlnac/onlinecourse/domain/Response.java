package cn.canlnac.onlinecourse.domain;

/**
 * 注册.
 */

public class Response<Body> {
    private Body body;
    private String status;
    private String jwt;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

