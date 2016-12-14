package cn.canlnac.onlinecourse.data.net;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口连接.
 */

public class RestApiConnection {
    /** api地址 */
    String API_BASE_URL = "http://120.24.221.156:8080/";

    //用户
    String API_USER = API_BASE_URL + "user";
    //文档
    String API_DOCUMENT = API_BASE_URL + "document";
    //课程
    String API_COURSE = API_BASE_URL + "course";
    String API_COURSES = API_BASE_URL + "courses";
    //评论
    String API_COMMENT = API_BASE_URL + "comment";
    //话题
    String API_CHAT = API_BASE_URL + "chat";
    String API_CHATS = API_BASE_URL + "chats";
    //目录
    String API_CATALOG = API_BASE_URL + "catalog";

    public RestApiConnection() {}

    /**
     * 注册请求
     * @param username  用户名
     * @param password  密码
     * @return Response 响应
     * @throws MalformedURLException
     */
    public Response registerFromApi(String username, String password) throws MalformedURLException, NoSuchAlgorithmException {
        Map<String, String> map = new HashMap<>();

        //MD5加密
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        password = String.format("%032X", new BigInteger(1, messageDigest.digest()));

        map.put("username", username);
        map.put("password", password);
        map.put("userStatus", "student");

        return APIConnection.create(METHOD.POST, API_USER, new Gson().toJson(map), null).request();
    }
}
