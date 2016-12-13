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
import java.util.List;
import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.AnswerEntity;
import cn.canlnac.onlinecourse.data.entity.CatalogEntity;
import cn.canlnac.onlinecourse.data.entity.ChatEntity;
import cn.canlnac.onlinecourse.data.entity.ChatListEntity;
import cn.canlnac.onlinecourse.data.entity.CommentListEntity;
import cn.canlnac.onlinecourse.data.entity.CourseEntity;
import cn.canlnac.onlinecourse.data.entity.CourseListEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentListEntity;
import cn.canlnac.onlinecourse.data.entity.FollowerEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordListEntity;
import cn.canlnac.onlinecourse.data.entity.MessageEntity;
import cn.canlnac.onlinecourse.data.entity.MessageListEntity;
import cn.canlnac.onlinecourse.data.entity.ProfileEntity;
import cn.canlnac.onlinecourse.data.entity.QuestionEntity;
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

    @Override
    public Observable<ProfileEntity> getUserProfile(int userId) {
        return null;
    }

    @Override
    public Observable<Void> updateUserProfile(Map<String, String> profile) {
        return null;
    }

    @Override
    public Observable<MessageListEntity> getMessages(int start, int count, boolean isRead) {
        return null;
    }

    @Override
    public Observable<MessageEntity> getMessage(int messageId) {
        return null;
    }

    @Override
    public Observable<Void> deleteMessage(int messageId) {
        return null;
    }

    @Override
    public Observable<LearnRecordListEntity> getOtherUserLearnRecord(int userId, int start, int count) {
        return null;
    }

    @Override
    public Observable<FollowerEntity> getFollower(int start, int count) {
        return null;
    }

    @Override
    public Observable<FollowerEntity> getUserFollowing(int userId, int start, int count) {
        return null;
    }

    @Override
    public Observable<Void> followUser(int userId) {
        return null;
    }

    @Override
    public Observable<Void> unfollowUser(int userId) {
        return null;
    }

    @Override
    public Observable<DocumentEntity> getDocument(int documentId) {
        return null;
    }

    @Override
    public Observable<Void> deleteDocument(int documentId) {
        return null;
    }

    @Override
    public Observable<CourseEntity> getCourse(int courseId) {
        return null;
    }

    @Override
    public Observable<Void> updateCourse(int courseId, Map<String, String> course) {
        return null;
    }

    @Override
    public Observable<Void> deleteCourse(int courseId) {
        return null;
    }

    @Override
    public Observable<Integer> createCourse(Map<String, String> course) {
        return null;
    }

    @Override
    public Observable<Void> likeCourse(int courseId) {
        return null;
    }

    @Override
    public Observable<Void> unlikeCourse(int courseId) {
        return null;
    }

    @Override
    public Observable<Void> favoriteCourse(int courseId) {
        return null;
    }

    @Override
    public Observable<Void> unfavoriteCourse(int courseId) {
        return null;
    }

    @Override
    public Observable<Integer> createDocument(int courseId, Map<String, String> document) {
        return null;
    }

    @Override
    public Observable<DocumentListEntity> getDocumentsInCourse(int courseId, int start, int count, String sort) {
        return null;
    }

    @Override
    public Observable<CommentListEntity> getCommentsInCourse(int courseId, int start, int count, String sort) {
        return null;
    }

    @Override
    public Observable<Integer> createCommentInCourse(int courseId, Map<String, Object> comment) {
        return null;
    }

    @Override
    public Observable<Integer> replyCommentInCourse(int courseId, int commentId, Map<String, Object> reply) {
        return null;
    }

    @Override
    public Observable<Integer> createCatalog(int courseId, Map<String, Object> catalog) {
        return null;
    }

    @Override
    public Observable<List<CatalogEntity>> getCatalogs(int courseId) {
        return null;
    }

    @Override
    public Observable<CourseListEntity> getCourses(int start, int count, String sort, List<String> departments) {
        return null;
    }

    @Override
    public Observable<Void> likeComment(int commentId) {
        return null;
    }

    @Override
    public Observable<Void> unlikeComment(int commentId) {
        return null;
    }

    @Override
    public Observable<ChatEntity> getChat(int chatId) {
        return null;
    }

    @Override
    public Observable<Void> deleteChat(int chatId) {
        return null;
    }

    @Override
    public Observable<Integer> createChat(Map<String, Object> chat) {
        return null;
    }

    @Override
    public Observable<Void> likeChat(int chatId) {
        return null;
    }

    @Override
    public Observable<Void> unlikeChat(int chatId) {
        return null;
    }

    @Override
    public Observable<Void> favoriteChat(int chatId) {
        return null;
    }

    @Override
    public Observable<Void> unfavoriteChat(int chatId) {
        return null;
    }

    @Override
    public Observable<CommentListEntity> getCommentsInChat(int chatId, int start, int count, String sort) {
        return null;
    }

    @Override
    public Observable<Integer> createCommentInChat(int chatId, Map<String, Object> comment) {
        return null;
    }

    @Override
    public Observable<Integer> replyCommentInChat(int chatId, int commentId, Map<String, Object> reply) {
        return null;
    }

    @Override
    public Observable<ChatListEntity> getChats(int start, int count, String sort) {
        return null;
    }

    @Override
    public Observable<CatalogEntity> getCatalog(int catalogId) {
        return null;
    }

    @Override
    public Observable<Void> updateCatalog(int catalogId, Map<String, Object> catalog) {
        return null;
    }

    @Override
    public Observable<Void> deleteCatalog(int catalogId) {
        return null;
    }

    @Override
    public Observable<Integer> createQuestion(int catalogId, Map<String, Object> question) {
        return null;
    }

    @Override
    public Observable<Void> updateQuestion(int catalogId, Map<String, Object> question) {
        return null;
    }

    @Override
    public Observable<Void> deleteQuestion(int catalogId) {
        return null;
    }

    @Override
    public Observable<QuestionEntity> getQuestion(int catalogId) {
        return null;
    }

    @Override
    public Observable<LearnRecordEntity> getLearnRecord(int catalogId) {
        return null;
    }

    @Override
    public Observable<Integer> createLearnRecord(int catalogId, Map<String, Object> learnRecord) {
        return null;
    }

    @Override
    public Observable<Void> updateLearnRecord(int catalogId, Map<String, Object> learnRecord) {
        return null;
    }

    @Override
    public Observable<Integer> getDocumentInCatalog(int catalogId, Map<String, Object> document) {
        return null;
    }

    @Override
    public Observable<DocumentListEntity> getDocumentsInCatalog(int catalogId, int start, int count, String sort) {
        return null;
    }

    @Override
    public Observable<AnswerEntity> getAnswer(int catalogId) {
        return null;
    }

    @Override
    public Observable<Integer> createAnser(int catalogId, Map<String, Object> answer) {
        return null;
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
