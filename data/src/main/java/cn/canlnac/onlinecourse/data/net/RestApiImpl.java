package cn.canlnac.onlinecourse.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import cn.canlnac.onlinecourse.data.exception.NetworkConnectionException;
import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import rx.Observable;

/**
 * 实现api接口
 */
public class RestApiImpl implements RestApi {
    private final Context context;

    /**
     * 构造函数
     *
     * @param context               {@link android.content.Context}               安卓内容上下文
     */
    public RestApiImpl(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
    }

    /**
     * 注册
     * @param username  用户名
     * @param password  密码
     * @return
     */
    @Override
    public Observable<RegisterEntity> register(String username, String password) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = registerFromApi(username, password); //注册
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    RegisterEntity registerEntity = new Gson().fromJson(response.body().string(), RegisterEntity.class);
                    subscriber.onNext(registerEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    /**
     * 注册请求
     * @param username  用户名
     * @param password  密码
     * @return Response 响应
     * @throws MalformedURLException
     */
    private Response registerFromApi(String username, String password) throws MalformedURLException, NoSuchAlgorithmException {
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

    /**
     * 检查设备是否连接网络
     *
     * @return 连接网络：true，连接失败：false
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    /**
     * 请求返回状态码错误
     * @param code  状态码
     * @return
     */
    private ResponseStatusException setCommentStatusError(int code) {
        switch (code) {
            case 400:
                return new ResponseStatusException(code, "参数错误");
            case 401:
                return new ResponseStatusException(code, "未登录");
            case 403:
                return new ResponseStatusException(code, "权限不足");
            case 404:
                return new ResponseStatusException(code, "资源不存在");
            case 409:
                return new ResponseStatusException(code, "资源冲突");
            case 500:
                return new ResponseStatusException(code, "服务器内部错误");
            default:
                return new ResponseStatusException(code,"未知错误");
        }
    }
}
