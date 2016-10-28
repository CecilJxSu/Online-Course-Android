package cn.canlnac.onlinecourse.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.net.MalformedURLException;

import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.data.entity.mapper.LoginEntityJsonMapper;
import cn.canlnac.onlinecourse.data.exception.NetworkConnectionException;
import rx.Observable;

/**
 * 实现api接口
 */
public class RestApiImpl implements RestApi {
    private final Context context;
    private final LoginEntityJsonMapper loginEntityJsonMapper;

    /**
     * 构造函数
     *
     * @param context               {@link android.content.Context}               安卓内容上下文
     * @param loginEntityJsonMapper {@link LoginEntityJsonMapper}   json处理类
     */
    public RestApiImpl(Context context, LoginEntityJsonMapper loginEntityJsonMapper) {
        if (context == null || loginEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.loginEntityJsonMapper = loginEntityJsonMapper;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录实体类
     */
    @RxLogObservable
    @Override
    public Observable<LoginEntity> login(String username, String password) {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseLogin = getLoginEntityFromApi();
                    if (responseLogin != null) {
                        subscriber.onNext(loginEntityJsonMapper.transformLoginEntity(responseLogin));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    /**
     * 登录请求
     *
     * @return 登录数据
     * @throws MalformedURLException URL格式错误
     */
    private String getLoginEntityFromApi() throws MalformedURLException {
        return APIConnection.createGET(API_LOGIN_POST).requestSyncCall();
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
