package cn.canlnac.onlinecourse.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.AnswerEntity;
import cn.canlnac.onlinecourse.data.entity.AnswerIdEntity;
import cn.canlnac.onlinecourse.data.entity.CatalogEntity;
import cn.canlnac.onlinecourse.data.entity.CatalogIdEntity;
import cn.canlnac.onlinecourse.data.entity.ChatEntity;
import cn.canlnac.onlinecourse.data.entity.ChatIdEntity;
import cn.canlnac.onlinecourse.data.entity.ChatListEntity;
import cn.canlnac.onlinecourse.data.entity.CommentEntity;
import cn.canlnac.onlinecourse.data.entity.CommentIdEntity;
import cn.canlnac.onlinecourse.data.entity.CommentListEntity;
import cn.canlnac.onlinecourse.data.entity.CourseEntity;
import cn.canlnac.onlinecourse.data.entity.CourseIdEntity;
import cn.canlnac.onlinecourse.data.entity.CourseListEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentIdEntity;
import cn.canlnac.onlinecourse.data.entity.DocumentListEntity;
import cn.canlnac.onlinecourse.data.entity.FollowerEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordIdEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordListEntity;
import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.data.entity.MessageEntity;
import cn.canlnac.onlinecourse.data.entity.MessageListEntity;
import cn.canlnac.onlinecourse.data.entity.ProfileEntity;
import cn.canlnac.onlinecourse.data.entity.QuestionIdEntity;
import cn.canlnac.onlinecourse.data.entity.QuestionListEntity;
import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import cn.canlnac.onlinecourse.data.entity.ReplyEntity;
import cn.canlnac.onlinecourse.data.entity.ReplyIdEntity;
import cn.canlnac.onlinecourse.data.entity.UploadEntity;
import cn.canlnac.onlinecourse.data.exception.NetworkConnectionException;
import cn.canlnac.onlinecourse.data.exception.ResponseStatusException;
import rx.Observable;

/**
 * 实现api接口
 */
public class RestApiImpl implements RestApi {
    private final Context context;
    private RestApiConnection restApiConnection;

    /**
     * 构造函数
     *
     * @param context           安卓内容上下文
     * @param restApiConnection 接口连接
     */
    public RestApiImpl(Context context, RestApiConnection restApiConnection) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }

        this.context = context.getApplicationContext();
        this.restApiConnection = restApiConnection;
    }

    /**
     * 注册
     * @param username  用户名
     * @param password  密码
     * @return
     */
    @Override
    public Observable<Integer> register(String username, String password) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.registerFromApi(username, password); //注册
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200 || (response.code() == 403 && response.body().toString().length() > 2)) {//状态码正确响应或封号
                    RegisterEntity registerEntity = new Gson().fromJson(response.body().string(), RegisterEntity.class);
                    subscriber.onNext(registerEntity.getUserId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<LoginEntity> login(String username, String password) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.loginFromApi(username, password); //登录
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    LoginEntity loginEntity = new Gson().fromJson(response.body().string(), LoginEntity.class);
                    //设置jwt
                    loginEntity.setJwt(response.header("Authentication"));

                    restApiConnection.setJwt(loginEntity.getJwt());
                    restApiConnection.setLoginData(loginEntity);
                    //完成
                    subscriber.onNext(loginEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<LoginEntity> getLogin() {
        return Observable.create(subscriber -> {
            try {
                LoginEntity loginEntity = restApiConnection.getLoginData(); //获取登录信息

                subscriber.onNext(loginEntity);
                subscriber.onCompleted();
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> setLogin(LoginEntity loginEntity) {
        return Observable.create(subscriber -> {
            try {
                restApiConnection.setLoginData(loginEntity); //设置登录信息

                subscriber.onNext(null);
                subscriber.onCompleted();
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    /**
     * 获取用户资料
     * @param userId            分页开始位置
     * @return ProfileEntity    用户资料
     */
    @Override
    public Observable<ProfileEntity> getUserProfile(int userId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getUserProfileFromApi(userId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    ProfileEntity profileEntity = new Gson().fromJson(response.body().string(), ProfileEntity.class);
                    //完成
                    subscriber.onNext(profileEntity);
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
     * 更新用户资料
     * @param profile   用户资料
     * @return Void
     */
    @Override
    public Observable<Void> updateUserProfile(Map<String, String> profile) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.updateUserProfileFromApi(profile);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                LoginEntity loginEntity = restApiConnection.getLoginData();
                if (profile.get("nickname")!=null){
                    loginEntity.setNickname(profile.get("nickname"));
                }
                if (profile.get("gender")!=null){
                    loginEntity.setGender(profile.get("gender"));
                }
                if (profile.get("iconUrl")!=null){
                    loginEntity.setIconUrl(profile.get("iconUrl"));
                }

                restApiConnection.setLoginData(loginEntity);

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
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
     * 获取用户消息列表
     * @param start                 分页开始位置
     * @param count                 分页返回数目
     * @param isRead                是否阅读，Y|N
     * @return MessageListEntity    消息列表
     */
    @Override
    public Observable<MessageListEntity> getMessages(@Nullable Integer start, @Nullable Integer count, @Nullable Boolean isRead) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getMessagesFromApi(start, count, isRead);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    MessageListEntity messageListEntity = new Gson().fromJson(response.body().string(), MessageListEntity.class);
                    //完成
                    subscriber.onNext(messageListEntity);
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
     * 获取单个消息
     * @param messageId         消息ID
     * @return MessageEntity    消息
     */
    @Override
    public Observable<MessageEntity> getMessage(int messageId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getMessageFromApi(messageId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    MessageEntity messageEntity = new Gson().fromJson(response.body().string(), MessageEntity.class);
                    //完成
                    subscriber.onNext(messageEntity);
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
     * 删除消息
     * @param messageId 消息ID
     * @return Void
     */
    @Override
    public Observable<Void> deleteMessage(int messageId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.deleteMessageFromApi(messageId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
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
     * 获取其它用户学习记录
     * @param userId    用户ID
     * @param start     分页开始位置
     * @param count     分页返回数目
     * @return LearnRecordListEntity
     */
    @Override
    public Observable<LearnRecordListEntity> getOtherUserLearnRecord(int userId, @Nullable Integer start, @Nullable Integer count) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getOtherUserLearnRecordFromApi(userId, start, count);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    LearnRecordListEntity learnRecordListEntity = new Gson().fromJson(response.body().string(), LearnRecordListEntity.class);
                    //完成
                    subscriber.onNext(learnRecordListEntity);
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
     * 获取粉丝
     * @param start 分页开始位置
     * @param count 分页返回数目
     * @return FollowerEntity
     */
    @Override
    public Observable<FollowerEntity> getFollower(@Nullable Integer start, @Nullable Integer count) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getFollowerFromApi(start, count);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    FollowerEntity followerEntity = new Gson().fromJson(response.body().string(), FollowerEntity.class);
                    //完成
                    subscriber.onNext(followerEntity);
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
     * 获取关注用户
     * @param userId    用户ID
     * @param start     分页开始位置
     * @param count     分页返回数目
     * @return FollowerEntity
     */
    @Override
    public Observable<FollowerEntity> getUserFollowing(int userId, @Nullable Integer start, @Nullable Integer count) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getUserFollowingFromApi(userId, start, count);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    FollowerEntity followerEntity = new Gson().fromJson(response.body().string(), FollowerEntity.class);
                    //完成
                    subscriber.onNext(followerEntity);
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
     * 关注用户
     * @param userId    用户ID
     * @return Void
     */
    @Override
    public Observable<Void> followUser(int userId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.followUserFromApi(userId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
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
     * 取消关注
     * @param userId    用户ID
     * @return Void
     */
    @Override
    public Observable<Void> unfollowUser(int userId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.unfollowUserFromApi(userId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
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
     * 获取我的话题
     * @param start                 分页开始位置
     * @param count                 分页返回数目
     * @return MessageListEntity    消息列表
     */
    @Override
    public Observable<ChatListEntity> getMyChats(@Nullable Integer start, @Nullable Integer count) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getMyChatsFromApi(start, count);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    ChatListEntity chatListEntity = new Gson().fromJson(response.body().string(), ChatListEntity.class);
                    //完成
                    subscriber.onNext(chatListEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<DocumentEntity> getDocument(int documentId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getDocumentFromApi(documentId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    DocumentEntity documentEntity = new Gson().fromJson(response.body().string(), DocumentEntity.class);
                    //完成
                    subscriber.onNext(documentEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> deleteDocument(int documentId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.deleteDocumentFromApi(documentId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<CourseEntity> getCourse(int courseId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getCourseFromApi(courseId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CourseEntity courseEntity = new Gson().fromJson(response.body().string(), CourseEntity.class);
                    //完成
                    subscriber.onNext(courseEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> updateCourse(int courseId, Map<String, String> course) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.updateCourseFromApi(courseId, course);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> deleteCourse(int courseId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.deleteCourseFromApi(courseId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createCourse(Map<String, String> course) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createCourseFromApi(course);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CourseIdEntity CourseIdEntity = new Gson().fromJson(response.body().string(),CourseIdEntity.class);
                    //完成
                    subscriber.onNext(CourseIdEntity.getCourseId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> likeCourse(int courseId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.likeCourseFromApi(courseId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> unlikeCourse(int courseId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.unlikeCourseFromApi(courseId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> favoriteCourse(int courseId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.favoriteCourseFromApi(courseId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> unfavoriteCourse(int courseId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.unfavoriteCourseFromApi(courseId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createDocument(int courseId, Map<String, String> document) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createDocumentFromApi(courseId, document);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    DocumentIdEntity documentIdEntity = new Gson().fromJson(response.body().string(),DocumentIdEntity.class);
                    //完成
                    subscriber.onNext(documentIdEntity.getDocumentId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<DocumentListEntity> getDocumentsInCourse(int courseId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getDocumentsInCourseFromApi(courseId, start, count, sort);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    DocumentListEntity documentListEntity = new Gson().fromJson(response.body().string(),DocumentListEntity.class);
                    //完成
                    subscriber.onNext(documentListEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<File> download(String fileUrl, File targetFile) {
        return Observable.create(subscriber -> {
            if (targetFile.exists()) {
                subscriber.onNext(targetFile);
                subscriber.onCompleted();
                return;
            }

            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                URL url = new URL(fileUrl);
                URLConnection conn = url.openConnection();

                InputStream bin = new BufferedInputStream(conn.getInputStream());
                FileOutputStream fout = new FileOutputStream(targetFile);

                byte[] bytes = new byte[512];
                int dataSize;
                while ((dataSize = bin.read(bytes)) != -1) {
                    fout.write(bytes, 0, dataSize);
                }

                bin.close();
                fout.close();

                subscriber.onNext(targetFile);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<CommentListEntity> getCommentsInCourse(int courseId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getCommentsInCourseFromApi(courseId, start, count, sort);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CommentListEntity commentListEntity = new Gson().fromJson(response.body().string(),CommentListEntity.class);
                    //完成
                    subscriber.onNext(commentListEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createCommentInCourse(int courseId, Map<String, Object> comment) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createCommentInCourseFromApi(courseId, comment);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CommentIdEntity commentIdEntity = new Gson().fromJson(response.body().string(),CommentIdEntity.class);
                    //完成
                    subscriber.onNext(commentIdEntity.getCommentId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> replyCommentInCourse(int courseId, int commentId, Map<String, Object> reply) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.replyCommentInCourseFromApi(courseId, commentId, reply);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    ReplyIdEntity replyIdEntity = new Gson().fromJson(response.body().string(),ReplyIdEntity.class);
                    //完成
                    subscriber.onNext(replyIdEntity.getReplyId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createCatalog(int courseId, Map<String, Object> catalog) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createCatalogFromApi(courseId, catalog);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CatalogIdEntity catalogIdEntity = new Gson().fromJson(response.body().string(),CatalogIdEntity.class);
                    //完成
                    subscriber.onNext(catalogIdEntity.getCatalogId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<List<CatalogEntity>> getCatalogs(int courseId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getCatalogsFromApi(courseId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CatalogEntity[] catalogEntities = new Gson().fromJson(response.body().string(),CatalogEntity[].class);
                    //完成
                    subscriber.onNext(Arrays.asList(catalogEntities));
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<CourseListEntity> getCourses(@Nullable Integer start, @Nullable Integer count, @Nullable String sort, List<String> departments) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getCoursesFromApi(start, count, sort, departments);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CourseListEntity courseListEntity = new Gson().fromJson(response.body().string(),CourseListEntity.class);
                    //完成
                    subscriber.onNext(courseListEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> likeComment(int commentId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.likeCommentFromApi(commentId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> unlikeComment(int commentId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.unlikeCommentFromApi(commentId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<CommentEntity> getComment(int commentId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getCommentFromApi(commentId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    CommentEntity commentEntity = new Gson().fromJson(response.body().string(),CommentEntity.class);
                    subscriber.onNext(commentEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<ReplyEntity> getReply(int replyId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getReplyFromApi(replyId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    ReplyEntity replyEntity = new Gson().fromJson(response.body().string(),ReplyEntity.class);
                    subscriber.onNext(replyEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<ChatEntity> getChat(int chatId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getChatFromApi(chatId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    ChatEntity chatEntity = new Gson().fromJson(response.body().string(),ChatEntity.class);
                    //完成
                    subscriber.onNext(chatEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> deleteChat(int chatId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.deleteChatFromApi(chatId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createChat(Map<String, Object> chat) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createChatFromApi(chat);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    ChatIdEntity chatIdEntity = new Gson().fromJson(response.body().string(),ChatIdEntity.class);
                    //完成
                    subscriber.onNext(chatIdEntity.getChatId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> likeChat(int chatId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.likeChatFromApi(chatId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> unlikeChat(int chatId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.unlikeChatFromApi(chatId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> favoriteChat(int chatId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.favoriteChatFromApi(chatId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> unfavoriteChat(int chatId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.unfavoriteChatFromApi(chatId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<CommentListEntity> getCommentsInChat(int chatId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getCommentsInChatFromApi(chatId, start, count, sort);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CommentListEntity commentListEntity = new Gson().fromJson(response.body().string(),CommentListEntity.class);
                    //完成
                    subscriber.onNext(commentListEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createCommentInChat(int chatId, Map<String, Object> comment) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createCommentInChatFromApi(chatId, comment);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CommentIdEntity commentIdEntity = new Gson().fromJson(response.body().string(),CommentIdEntity.class);
                    //完成
                    subscriber.onNext(commentIdEntity.getCommentId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> replyCommentInChat(int chatId, int commentId, Map<String, Object> reply) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.replyCommentInChatFromApi(chatId, commentId, reply);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    ReplyIdEntity replyIdEntity = new Gson().fromJson(response.body().string(),ReplyIdEntity.class);
                    //完成
                    subscriber.onNext(replyIdEntity.getReplyId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<ChatListEntity> getChats(@Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getChatsFromApi(start, count, sort);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    ChatListEntity chatListEntity = new Gson().fromJson(response.body().string(),ChatListEntity.class);
                    //完成
                    subscriber.onNext(chatListEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<CatalogEntity> getCatalog(int catalogId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getCatalogFromApi(catalogId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    CatalogEntity catalogEntity = new Gson().fromJson(response.body().string(),CatalogEntity.class);
                    //完成
                    subscriber.onNext(catalogEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> updateCatalog(int catalogId, Map<String, Object> catalog) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.updateCatalogFromApi(catalogId, catalog);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> deleteCatalog(int catalogId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.deleteCatalogFromApi(catalogId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createQuestion(int catalogId, Map<String, Object> question) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createQuestionFromApi(catalogId, question);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    QuestionIdEntity questionIdEntity = new Gson().fromJson(response.body().string(),QuestionIdEntity.class);
                    //完成
                    subscriber.onNext(questionIdEntity.getQuestionId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> updateQuestion(int catalogId, Map<String, Object> question) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.updateQuestionFromApi(catalogId, question);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> deleteQuestion(int catalogId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.deleteQuestionFromApi(catalogId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<QuestionListEntity> getQuestion(int catalogId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getQuestionFromApi(catalogId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    QuestionListEntity questionListEntity = new Gson().fromJson(response.body().string(),QuestionListEntity.class);
                    //完成
                    subscriber.onNext(questionListEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<LearnRecordEntity> getLearnRecord(int catalogId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getLearnRecordFromApi(catalogId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    LearnRecordEntity learnRecordEntity = new Gson().fromJson(response.body().string(),LearnRecordEntity.class);
                    //完成
                    subscriber.onNext(learnRecordEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createLearnRecord(int catalogId, Map<String, Object> learnRecord) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createLearnRecordFromApi(catalogId,learnRecord);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    LearnRecordIdEntity learnRecordIdEntity = new Gson().fromJson(response.body().string(),LearnRecordIdEntity.class);
                    //完成
                    subscriber.onNext(learnRecordIdEntity.getLearnRecordId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Void> updateLearnRecord(int catalogId, Map<String, Object> learnRecord) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.updateLearnRecordFromApi(catalogId, learnRecord);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    //完成
                    subscriber.onNext(null);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createDocumentInCatalog(int catalogId, Map<String, Object> document) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createDocumentInCatalogFromApi(catalogId, document);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    DocumentIdEntity documentIdEntity = new Gson().fromJson(response.body().string(),DocumentIdEntity.class);
                    //完成
                    subscriber.onNext(documentIdEntity.getDocumentId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<DocumentListEntity> getDocumentsInCatalog(int catalogId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getDocumentsInCatalogFromApi(catalogId, start, count, sort);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    DocumentListEntity documendtListEntity = new Gson().fromJson(response.body().string(),DocumentListEntity.class);
                    //完成
                    subscriber.onNext(documendtListEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<AnswerEntity> getAnswer(int catalogId) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.getAnswerFromApi(catalogId);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    AnswerEntity answerEntity = new Gson().fromJson(response.body().string(),AnswerEntity.class);
                    //完成
                    subscriber.onNext(answerEntity);
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<Integer> createAnser(int catalogId, Map<String, Object> answer) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.createAnserFromApi(catalogId, answer);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    AnswerIdEntity answerIdEntity = new Gson().fromJson(response.body().string(),AnswerIdEntity.class);
                    //完成
                    subscriber.onNext(answerIdEntity.getAnswerId());
                    subscriber.onCompleted();
                } else {//状态码错误
                    subscriber.onError(setCommentStatusError(response.code()));
                }
            } catch (Exception e) {
                subscriber.onError(new NetworkConnectionException(e.getCause()));
            }
        });
    }

    @Override
    public Observable<List<UploadEntity>> uploadFiles(List<File> files) {
        return Observable.create(subscriber -> {
            if (!isThereInternetConnection()) {//检查网络
                subscriber.onError(new NetworkConnectionException());
                return;
            }

            try {
                Response response = restApiConnection.uploadFilesFromApi(files);
                if (response == null) {//网络异常
                    subscriber.onError(new NetworkConnectionException());
                    return;
                }

                if (response.code() == 200) {//状态码正确响应
                    UploadEntity[] uploadEntities = new Gson().fromJson(response.body().string(),UploadEntity[].class);
                    //完成
                    subscriber.onNext(Arrays.asList(uploadEntities));
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
