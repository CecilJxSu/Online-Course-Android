package cn.canlnac.onlinecourse.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 响应返回的实体类.
 */

public class ResponseEntity<Body> {
    @SerializedName("responseStatus")
    private String responseStatus;

    @SerializedName("responseBody")
    private Body responseBody;

    @SerializedName("jwtHeader")
    private String jwtHeader;

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Body getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Body responseBody) {
        this.responseBody = responseBody;
    }

    public String getJwtHeader() {
        return jwtHeader;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }
}
