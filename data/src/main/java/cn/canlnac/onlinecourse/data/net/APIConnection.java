package cn.canlnac.onlinecourse.data.net;

import android.support.annotation.Nullable;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 发起请求，从服务器中获取数据
 */

public class APIConnection implements Callable<Response> {

    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String JWT_LABEL = "";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private METHOD method;
    private URL url;
    private String body;
    private String jwt;

    private APIConnection(METHOD method, String url, String body, String jwt) throws MalformedURLException {
        this.method = method;
        this.url = new URL(url);
        this.body = body;
        this.jwt = jwt;
    }

    /**
     * 创建APIConnection实例
     * @param method                    请求方法
     * @param url                       url
     * @param body                      请求数据
     * @param jwt                       头部的json web token验证信息
     * @return APIConnection            APIConnection实例
     * @throws MalformedURLException    邮箱格式不对
     */
    public static APIConnection create(METHOD method, String url, @Nullable String body, @Nullable String jwt) throws MalformedURLException {
        if (body == null || body.isEmpty()) {
            body = "{}";
        }

        return new APIConnection(method, url, body, jwt);
    }

    /**
     * 请求
     * @return Response 响应
     */
    @Nullable
    public Response request() {
        //创建请求客户端
        OkHttpClient okHttpClient = this.createClient();
        //Builder
        Request.Builder builder = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON);
        //创建请求数据
        RequestBody requestBody = RequestBody.create(JSON, body);
        //添加验证信息
        if (jwt != null) {
            builder.addHeader(JWT_LABEL, jwt);
        }
        switch (method) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(requestBody);
                break;
            case PUT:
                builder.put(requestBody);
                break;
            case DELETE:
                builder.delete(requestBody);
                break;
        }
        //build
        final Request request = builder.build();

        try {
            //直接返回数据
            return okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private OkHttpClient createClient() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(10000, TimeUnit.MILLISECONDS);
        okHttpClient.setConnectTimeout(15000, TimeUnit.MILLISECONDS);

        return okHttpClient;
    }

    @Override
    public Response call() throws Exception {
        return request();
    }
}