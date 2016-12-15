package cn.canlnac.onlinecourse.data.net;

import android.content.Context;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.canlnac.onlinecourse.data.cache.FileManager;

/**
 * 接口连接.
 */

public class RestApiConnection {
    /** api地址 */
    String API_BASE_URL = "http://120.24.221.156:8080/";

    //登录
    String API_LOGIN = API_BASE_URL + "login";
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

    private final Context context;
    private final FileManager fileManager;

    public RestApiConnection(Context context) {
        this.context = context;
        fileManager = new FileManager();
    }

    /**
     * 获取jwt
     * @return
     */
    public String getJwt() {
        return fileManager.getStringFromPreferences(context,"jwt","jwt");
    }

    /**
     * 设置偏好
     * @param jwt   json web token
     */
    public void setJwt(String jwt) {
        fileManager.writeStringToPreferences(context,"jwt","jwt",jwt);
    }

    /**
     * 注册请求
     * @param username  用户名
     * @param password  密码
     * @return Response 响应
     * @throws MalformedURLException
     * @throws NoSuchAlgorithmException
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

        return APIConnection.create(METHOD.POST, API_USER, new Gson().toJson(map), getJwt()).request();
    }

    /**
     * 登录请求
     * @param username  用户名
     * @param password  密码
     * @return
     * @throws MalformedURLException
     * @throws NoSuchAlgorithmException
     */
    public Response loginFromApi(String username, String password) throws MalformedURLException, NoSuchAlgorithmException {
        Map<String, String> map = new HashMap<>();

        //MD5加密
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        password = String.format("%032X", new BigInteger(1, messageDigest.digest()));

        map.put("username", username);
        map.put("password", password);

        return APIConnection.create(METHOD.POST, API_LOGIN, new Gson().toJson(map), getJwt()).request();
    }

    /**
     * 获取用户资料
     * @param userId    用户ID
     * @return Response 响应
     * @throws MalformedURLException
     */
    public Response getUserProfileFromApi(int userId) throws MalformedURLException {
        return null;
    }

    /**
     * 更新用户资料
     * @param profile   用户资料
     * @return Response 响应
     * @throws MalformedURLException
     */
    public Response updateUserProfileFromApi(Map<String, String> profile) throws MalformedURLException {
        return null;
    }

    /**
     * 获取用户消息列表
     * @param start     分页开始位置
     * @param count     分页返回数目
     * @param isRead    是否阅读，Y|N
     * @return Response 响应
     * @throws MalformedURLException
     */
    public Response getMessagesFromApi(int start, int count, boolean isRead) throws MalformedURLException {
        return null;
    }

    /**
     * 获取单个消息
     * @param messageId 消息ID
     * @return Response 响应
     */
    public Response getMessageFromApi(int messageId) throws MalformedURLException {
        return null;
    }

    public Response deleteMessageFromApi(int messageId) throws MalformedURLException {
        return null;
    }

    public Response getOtherUserLearnRecordFromApi(int userId, int start, int count) throws MalformedURLException {
        return null;
    }

    public Response getFollowerFromApi(int start, int count) throws MalformedURLException {
        return null;
    }

    public Response getUserFollowingFromApi(int userId, int start, int count) throws MalformedURLException {
        return null;
    }

    public Response followUserFromApi(int userId) throws MalformedURLException {
        return null;
    }

    public Response unfollowUserFromApi(int userId) throws MalformedURLException {
        return null;
    }

    public Response getDocumentFromApi(int documentId) throws MalformedURLException {
        return null;
    }

    public Response deleteDocumentFromApi(int documentId) throws MalformedURLException {
        return null;
    }

    public Response getCourseFromApi(int courseId) throws MalformedURLException {
        return null;
    }

    public Response updateCourseFromApi(int courseId, Map<String, String> course) throws MalformedURLException {
        return null;
    }

    public Response deleteCourseFromApi(int courseId) throws MalformedURLException {
        return null;
    }

    public Response createCourseFromApi(Map<String, String> course) throws MalformedURLException {
        return null;
    }

    public Response likeCourseFromApi(int courseId) throws MalformedURLException {
        return null;
    }

    public Response unlikeCourseFromApi(int courseId) throws MalformedURLException {
        return null;
    }

    public Response favoriteCourseFromApi(int courseId) throws MalformedURLException {
        return null;
    }

    public Response unfavoriteCourseFromApi(int courseId) throws MalformedURLException {
        return null;
    }

    public Response createDocumentFromApi(int courseId, Map<String, String> document) throws MalformedURLException {
        return null;
    }

    public Response getDocumentsInCourseFromApi(int courseId, int start, int count, String sort) throws MalformedURLException {
        return null;
    }

    public Response getCommentsInCourseFromApi(int courseId, int start, int count, String sort) throws MalformedURLException {
        return null;
    }

    public Response createCommentInCourseFromApi(int courseId, Map<String, Object> comment) throws MalformedURLException {
        return null;
    }

    public Response replyCommentInCourseFromApi(int courseId, int commentId, Map<String, Object> reply) throws MalformedURLException {
        return null;
    }

    public Response createCatalogFromApi(int courseId, Map<String, Object> catalog) throws MalformedURLException {
        return null;
    }

    public Response getCatalogsFromApi(int courseId) throws MalformedURLException {
        return null;
    }

    public Response getCoursesFromApi(int start, int count, String sort, List<String> departments) throws MalformedURLException {
        return null;
    }

    public Response likeCommentFromApi(int commentId) throws MalformedURLException {
        return null;
    }

    public Response unlikeCommentFromApi(int commentId) throws MalformedURLException {
        return null;
    }

    public Response getChatFromApi(int chatId) throws MalformedURLException {
        return null;
    }

    public Response deleteChatFromApi(int chatId) throws MalformedURLException {
        return null;
    }

    public Response createChatFromApi(Map<String, Object> chat) throws MalformedURLException {
        return null;
    }

    public Response likeChatFromApi(int chatId) throws MalformedURLException {
        return null;
    }

    public Response unlikeChatFromApi(int chatId) throws MalformedURLException {
        return null;
    }

    public Response favoriteChatFromApi(int chatId) throws MalformedURLException {
        return null;
    }

    public Response unfavoriteChatFromApi(int chatId) throws MalformedURLException {
        return null;
    }

    public Response getCommentsInChatFromApi(int chatId, int start, int count, String sort) throws MalformedURLException {
        return null;
    }

    public Response createCommentInChatFromApi(int chatId, Map<String, Object> comment) throws MalformedURLException {
        return null;
    }

    public Response replyCommentInChatFromApi(int chatId, int commentId, Map<String, Object> reply) throws MalformedURLException {
        return null;
    }

    public Response getChatsFromApi(int start, int count, String sort) throws MalformedURLException {
        return null;
    }

    public Response getCatalogFromApi(int catalogId) throws MalformedURLException {
        return null;
    }

    public Response updateCatalogFromApi(int catalogId, Map<String, Object> catalog) throws MalformedURLException {
        return null;
    }

    public Response deleteCatalogFromApi(int catalogId) throws MalformedURLException {
        return null;
    }

    public Response createQuestionFromApi(int catalogId, Map<String, Object> question) throws MalformedURLException {
        return null;
    }

    public Response updateQuestionFromApi(int catalogId, Map<String, Object> question) throws MalformedURLException {
        return null;
    }

    public Response deleteQuestionFromApi(int catalogId) throws MalformedURLException {
        return null;
    }

    public Response getQuestionFromApi(int catalogId) throws MalformedURLException {
        return null;
    }

    public Response getLearnRecordFromApi(int catalogId) throws MalformedURLException {
        return null;
    }

    public Response createLearnRecordFromApi(int catalogId, Map<String, Object> learnRecord) throws MalformedURLException {
        return null;
    }

    public Response updateLearnRecordFromApi(int catalogId, Map<String, Object> learnRecord) throws MalformedURLException {
        return null;
    }

    public Response getDocumentInCatalogFromApi(int catalogId, Map<String, Object> document) throws MalformedURLException {
        return null;
    }

    public Response getDocumentsInCatalogFromApi(int catalogId, int start, int count, String sort) throws MalformedURLException {
        return null;
    }

    public Response getAnswerFromApi(int catalogId) throws MalformedURLException {
        return null;
    }

    public Response createAnserFromApi(int catalogId, Map<String, Object> answer) throws MalformedURLException {
        return null;
    }
}
