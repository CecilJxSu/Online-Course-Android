package cn.canlnac.onlinecourse.domain;

/**
 * 响应.
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

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}

