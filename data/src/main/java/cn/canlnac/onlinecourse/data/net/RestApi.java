package cn.canlnac.onlinecourse.data.net;

import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import rx.Observable;

/**
 * Rest api 接口，从后台获取数据
 */

public interface RestApi {
    /** api地址 */
    String API_BASE_URL = "http://120.24.221.156:8080/";

    // user
    /** 用户 */
    String API_USER = API_BASE_URL + "user";

    Observable<RegisterEntity> register(String username, String password, String email);
}
