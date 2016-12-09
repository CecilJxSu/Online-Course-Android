package cn.canlnac.onlinecourse.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

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
     * @param email     邮箱
     * @return
     */
    @Override
    public Observable<RegisterEntity> register(String username, String password, String email) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = registerFromApi(username, password, email); //注册
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    RegisterEntity registerEntity = new Gson().fromJson(response.body().toString(), RegisterEntity.class);

                    subscriber.onNext(registerEntity);
                    subscriber.onCompleted();
                    return;
                }

                switch (response.code()) {//状态码异常
                    case 400:
                        subscriber.onError(new ResponseStatusException(400, "参数错误"));
                        break;
                    case 409:
                        subscriber.onError(new ResponseStatusException(409, "用户或邮箱已经被注册"));
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
     * @param email     邮箱
     * @return Response 响应
     * @throws MalformedURLException
     */
    private Response registerFromApi(String username, String password, String email) throws MalformedURLException, NoSuchAlgorithmException {
        Map<String, String> map = new HashMap<>();

        //MD5加密
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        password = new String(messageDigest.digest());

        map.put("username", username);
        map.put("password", password);
        map.put("email", email);

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
}
