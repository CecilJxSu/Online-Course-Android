package cn.canlnac.onlinecourse.data.net;

import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import rx.Observable;

/**
 * Rest api 接口，从后台获取数据
 */

public interface RestApi {
    /** api地址 */
    String API_BASE_URL = "http://120.24.221.156:8080/";

    // login
    /** 登录 */
    String API_LOGIN_POST = API_BASE_URL + "login";

    // user
    /** 用户 */
    String API_USER = API_BASE_URL + "user";

    Observable<LoginEntity> login(String username, String password);
}
