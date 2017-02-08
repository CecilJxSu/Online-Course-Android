package cn.canlnac.onlinecourse.data.net;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;

import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.canlnac.onlinecourse.data.cache.FileManager;
import cn.canlnac.onlinecourse.data.entity.LoginEntity;

/**
 * 接口连接.
 */

public class RestApiConnection {
    /** api地址 */
    public static final String API_BASE_URL = "http://www.canlnac.cn:8080/";

    //登录
    public static final String API_LOGIN = API_BASE_URL + "login";
    //用户
    public static final String API_USER = API_BASE_URL + "user";
    //文档
    public static final String API_DOCUMENT = API_BASE_URL + "document";
    //课程
    public static final String API_COURSE = API_BASE_URL + "course";
    public static final String API_COURSES = API_BASE_URL + "courses";
    //评论
    public static final String API_COMMENT = API_BASE_URL + "comment";
    //回复
    public static final String API_REPLY = API_BASE_URL + "reply";
    //话题
    public static final String API_CHAT = API_BASE_URL + "chat";
    public static final String API_CHATS = API_BASE_URL + "chats";
    //目录
    public static final String API_CATALOG = API_BASE_URL + "catalog";

    //文件
    public static final String API_FILE = API_BASE_URL + "file";

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
     * 获取登陆信息
     * @return
     */
    public LoginEntity getLoginData() {
        String loginData = fileManager.getStringFromPreferences(context,"loginData","loginData");
        return new Gson().fromJson(loginData, LoginEntity.class);
    }

    /**
     * 设置登陆信息
     * @param loginEntity 登陆信息
     */
    public void setLoginData(LoginEntity loginEntity) {
        fileManager.writeStringToPreferences(context,"loginData","loginData", new Gson().toJson(loginEntity));
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
        return APIConnection.create(METHOD.GET, API_USER + "/" + userId + "/profile", null, getJwt()).request();
    }

    /**
     * 更新用户资料
     * @param profile   用户资料
     * @return Response 响应
     * @throws MalformedURLException
     */
    public Response updateUserProfileFromApi(Map<String, String> profile) throws MalformedURLException {
        return APIConnection.create(METHOD.PUT, API_USER + "/profile", new Gson().toJson(profile), getJwt()).request();
    }

    /**
     * 获取用户消息列表
     * @param start     分页开始位置
     * @param count     分页返回数目
     * @param isRead    是否阅读，Y|N
     * @return Response 响应
     * @throws MalformedURLException
     */
    public Response getMessagesFromApi(@Nullable Integer start, @Nullable Integer count, @Nullable Boolean isRead) throws MalformedURLException {
        String MESSAGES = API_USER + "/messages?";
        if (null != start) {
            MESSAGES += "start="+start+"&";
        }
        if (null != count) {
            MESSAGES += "count="+count+"&";
        }
        if (null != isRead) {
            MESSAGES += "isRead="+(isRead?"Y":"N")+"&";
        }
        return APIConnection.create(METHOD.GET, MESSAGES, null, getJwt()).request();
    }

    /**
     * 获取单个消息
     * @param messageId 消息ID
     * @return Response 响应
     */
    public Response getMessageFromApi(int messageId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_USER+"/message/"+messageId, null, getJwt()).request();
    }

    public Response deleteMessageFromApi(int messageId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_USER+"/message/"+messageId, null, getJwt()).request();
    }

    public Response getOtherUserLearnRecordFromApi(int userId, @Nullable Integer start, @Nullable Integer count) throws MalformedURLException {
        String API_LEARN_RECORD = API_USER + "/" + userId + "/learnRecord?";
        if (null != start) {
            API_LEARN_RECORD += "start=" + start + "&";
        }
        if (null != count) {
            API_LEARN_RECORD += "count=" + count + "&";
        }
        return APIConnection.create(METHOD.GET, API_LEARN_RECORD, null, getJwt()).request();
    }

    public Response getFollowerFromApi(@Nullable Integer start, @Nullable Integer count) throws MalformedURLException {
        String API_FOLLOWER = API_USER + "/follower?";
        if (null != start) {
            API_FOLLOWER += "start=" + start + "&";
        }
        if (null != count) {
            API_FOLLOWER += "count=" + count + "&";
        }
        return APIConnection.create(METHOD.GET, API_FOLLOWER, null, getJwt()).request();
    }

    public Response getUserFollowingFromApi(int userId, @Nullable Integer start, @Nullable Integer count) throws MalformedURLException {
        String API_FOLLOWER = API_USER + "/" + userId + "/following?";
        if (null != start) {
            API_FOLLOWER += "start=" + start + "&";
        }
        if (null != count) {
            API_FOLLOWER += "count=" + count + "&";
        }
        return APIConnection.create(METHOD.GET, API_FOLLOWER, null, getJwt()).request();
    }

    public Response followUserFromApi(int userId) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_USER + "/" + userId + "/following", null, getJwt()).request();
    }

    public Response unfollowUserFromApi(int userId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_USER + "/" + userId + "/following", null, getJwt()).request();
    }

    /**
     * 获取我的话题
     * @param start     分页开始位置
     * @param count     分页返回数目
     * @return Response 响应
     * @throws MalformedURLException
     */
    public Response getMyChatsFromApi(@Nullable Integer start, @Nullable Integer count) throws MalformedURLException {
        String CHATS = API_USER + "/chats?";
        if (null != start) {
            CHATS += "start="+start+"&";
        }
        if (null != count) {
            CHATS += "count="+count+"&";
        }
        return APIConnection.create(METHOD.GET, CHATS, null, getJwt()).request();
    }

    public Response getDocumentFromApi(int documentId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_DOCUMENT + "/" + documentId, null, getJwt()).request();
    }

    public Response deleteDocumentFromApi(int documentId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_DOCUMENT + "/" + documentId, null, getJwt()).request();
    }

    public Response getCourseFromApi(int courseId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_COURSE + "/" + courseId, null, getJwt()).request();
    }

    public Response updateCourseFromApi(int courseId, Map<String, String> course) throws MalformedURLException {
        return APIConnection.create(METHOD.PUT, API_COURSE + "/" + courseId, new Gson().toJson(course), getJwt()).request();
    }

    public Response deleteCourseFromApi(int courseId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_COURSE + "/" + courseId, null, getJwt()).request();
    }

    public Response createCourseFromApi(Map<String, String> course) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_COURSE, new Gson().toJson(course), getJwt()).request();
    }

    public Response likeCourseFromApi(int courseId) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_COURSE + "/" + courseId + "/like", null, getJwt()).request();
    }

    public Response unlikeCourseFromApi(int courseId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_COURSE + "/" + courseId + "/like", null, getJwt()).request();
    }

    public Response favoriteCourseFromApi(int courseId) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_COURSE + "/" + courseId + "/favorite", null, getJwt()).request();
    }

    public Response unfavoriteCourseFromApi(int courseId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_COURSE + "/" + courseId + "/favorite", null, getJwt()).request();
    }

    public Response createDocumentFromApi(int courseId, Map<String, String> document) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_COURSE + "/" + courseId + "/document", new Gson().toJson(document), getJwt()).request();
    }

    public Response getDocumentsInCourseFromApi(int courseId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) throws MalformedURLException {
        String API_DOCUMENTS = API_COURSE + "/" + courseId + "/documents?";
        if (null != start) {
            API_DOCUMENTS += "start=" + start + "&";
        }
        if (null != count) {
            API_DOCUMENTS += "count=" + count + "&";
        }
        if (null != sort) {
            API_DOCUMENTS += "sort=" + sort + "&";
        }
        return APIConnection.create(METHOD.GET, API_DOCUMENTS, null, getJwt()).request();
    }

    public Response getCommentsInCourseFromApi(int courseId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) throws MalformedURLException {
        String API_COMMENTS = API_COURSE + "/" + courseId + "/comments?";
        if (null != start) {
            API_COMMENTS += "start=" + start + "&";
        }
        if (null != count) {
            API_COMMENTS += "count=" + count + "&";
        }
        if (null != sort) {
            API_COMMENTS += "sort=" + sort + "&";
        }
        return APIConnection.create(METHOD.GET, API_COMMENTS, null, getJwt()).request();
    }

    public Response createCommentInCourseFromApi(int courseId, Map<String, Object> comment) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_COURSE + "/" + courseId + "/comment", new Gson().toJson(comment), getJwt()).request();
    }

    public Response replyCommentInCourseFromApi(int courseId, int commentId, Map<String, Object> reply) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_COURSE + "/" + courseId + "/comment/" + commentId, new Gson().toJson(reply), getJwt()).request();
    }

    public Response createCatalogFromApi(int courseId, Map<String, Object> catalog) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_COURSE + "/" + courseId + "/catalog", new Gson().toJson(catalog), getJwt()).request();
    }

    public Response getCatalogsFromApi(int courseId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_COURSE + "/" + courseId + "/catalogs", null, getJwt()).request();
    }

    public Response getCoursesFromApi(@Nullable Integer start, @Nullable Integer count, @Nullable String sort, @Nullable List<String> departments) throws MalformedURLException {
        String API_COURSES_LIST = API_COURSES + "?";
        if (null != start) {
            API_COURSES_LIST += "start=" + start + "&";
        }
        if (null != count) {
            API_COURSES_LIST += "count=" + count + "&";
        }
        if (null != sort) {
            API_COURSES_LIST += "sort=" + sort + "&";
        }
        if (null != departments) {
            for (String department:departments) {
                API_COURSES_LIST += "departments=" + department + "&";
            }
        }
        return APIConnection.create(METHOD.GET, API_COURSES_LIST, null, getJwt()).request();
    }

    public Response likeCommentFromApi(int commentId) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_COMMENT + "/" + commentId + "/like", null, getJwt()).request();
    }

    public Response unlikeCommentFromApi(int commentId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_COMMENT + "/" + commentId + "/like", null, getJwt()).request();
    }

    public Response getCommentFromApi(int commentId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_COMMENT + "/" + commentId, null, getJwt()).request();
    }

    public Response getReplyFromApi(int replyId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_REPLY + "/" + replyId, null, getJwt()).request();
    }

    public Response getChatFromApi(int chatId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_CHAT + "/" + chatId, null, getJwt()).request();
    }

    public Response deleteChatFromApi(int chatId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_CHAT + "/" + chatId, null, getJwt()).request();
    }

    public Response createChatFromApi(Map<String, Object> chat) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_CHAT, new Gson().toJson(chat), getJwt()).request();
    }

    public Response likeChatFromApi(int chatId) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_CHAT + "/" + chatId + "/like", null, getJwt()).request();
    }

    public Response unlikeChatFromApi(int chatId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_CHAT + "/" + chatId + "/like", null, getJwt()).request();
    }

    public Response favoriteChatFromApi(int chatId) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_CHAT + "/" + chatId + "/favorite", null, getJwt()).request();
    }

    public Response unfavoriteChatFromApi(int chatId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_CHAT + "/" + chatId + "/favorite", null, getJwt()).request();
    }

    public Response getCommentsInChatFromApi(int chatId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) throws MalformedURLException {
        String API_COMMENTS = API_CHAT + "/" + chatId + "/comments?";
        if (null != start) {
            API_COMMENTS += "start=" + start + "&";
        }
        if (null != count) {
            API_COMMENTS += "count=" + count + "&";
        }
        if (null != sort) {
            API_COMMENTS += "sort=" + sort + "&";
        }
        return APIConnection.create(METHOD.GET, API_COMMENTS, null, getJwt()).request();
    }

    public Response createCommentInChatFromApi(int chatId, Map<String, Object> comment) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_CHAT + "/" + chatId + "/comment", new Gson().toJson(comment), getJwt()).request();
    }

    public Response replyCommentInChatFromApi(int chatId, int commentId, Map<String, Object> reply) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_CHAT + "/" + chatId + "/comment/" + commentId, new Gson().toJson(reply), getJwt()).request();
    }

    public Response getChatsFromApi(@Nullable Integer start, @Nullable Integer count, @Nullable String sort) throws MalformedURLException {
        String API_CHATS_LIST = API_CHATS + "?";
        if (null != start) {
            API_CHATS_LIST += "start=" + start + "&";
        }
        if (null != count) {
            API_CHATS_LIST += "count=" + count + "&";
        }
        if (null != sort) {
            API_CHATS_LIST += "sort=" + sort + "&";
        }
        return APIConnection.create(METHOD.GET, API_CHATS_LIST, null, getJwt()).request();
    }

    public Response getCatalogFromApi(int catalogId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_CATALOG + "/" + catalogId, null, getJwt()).request();
    }

    public Response updateCatalogFromApi(int catalogId, Map<String, Object> catalog) throws MalformedURLException {
        return APIConnection.create(METHOD.PUT, API_CATALOG + "/" + catalogId, new Gson().toJson(catalog), getJwt()).request();
    }

    public Response deleteCatalogFromApi(int catalogId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_CATALOG + "/" + catalogId, null, getJwt()).request();
    }

    public Response createQuestionFromApi(int catalogId, Map<String, Object> question) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_CATALOG + "/" + catalogId + "/question", new Gson().toJson(question), getJwt()).request();
    }

    public Response updateQuestionFromApi(int catalogId, Map<String, Object> question) throws MalformedURLException {
        return APIConnection.create(METHOD.PUT, API_CATALOG + "/" + catalogId + "/question", new Gson().toJson(question), getJwt()).request();
    }

    public Response deleteQuestionFromApi(int catalogId) throws MalformedURLException {
        return APIConnection.create(METHOD.DELETE, API_CATALOG + "/" + catalogId + "/question", null, getJwt()).request();
    }

    public Response getQuestionFromApi(int catalogId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_CATALOG + "/" + catalogId + "/question", null, getJwt()).request();
    }

    public Response getLearnRecordFromApi(int catalogId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_CATALOG + "/" + catalogId + "/learnRecord", null, getJwt()).request();
    }

    public Response createLearnRecordFromApi(int catalogId, Map<String, Object> learnRecord) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_CATALOG + "/" + catalogId + "/learnRecord", new Gson().toJson(learnRecord), getJwt()).request();
    }

    public Response updateLearnRecordFromApi(int catalogId, Map<String, Object> learnRecord) throws MalformedURLException {
        return APIConnection.create(METHOD.PUT, API_CATALOG + "/" + catalogId + "/learnRecord", new Gson().toJson(learnRecord), getJwt()).request();
    }

    public Response createDocumentInCatalogFromApi(int catalogId, Map<String, Object> document) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_CATALOG + "/" + catalogId + "/document", new Gson().toJson(document), getJwt()).request();
    }

    public Response getDocumentsInCatalogFromApi(int catalogId, @Nullable Integer start, @Nullable Integer count, @Nullable String sort) throws MalformedURLException {
        String API_DOCUMENTS = API_CATALOG + "/" + catalogId + "/documents?";
        if (null != start) {
            API_DOCUMENTS += "start=" + start + "&";
        }
        if (null != count) {
            API_DOCUMENTS += "count=" + count + "&";
        }
        if (null != sort) {
            API_DOCUMENTS += "sort=" + sort + "&";
        }
        return APIConnection.create(METHOD.GET, API_DOCUMENTS, null, getJwt()).request();
    }

    public Response getAnswerFromApi(int catalogId) throws MalformedURLException {
        return APIConnection.create(METHOD.GET, API_CATALOG + "/" + catalogId + "/answer", null, getJwt()).request();
    }

    public Response createAnserFromApi(int catalogId, Map<String, Object> answer) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_CATALOG + "/" + catalogId + "/answer", new Gson().toJson(answer), getJwt()).request();
    }

    public Response uploadFilesFromApi(List<File> files) throws MalformedURLException {
        return APIConnection.create(METHOD.POST, API_FILE, null, getJwt()).upload(files);
    }
}
