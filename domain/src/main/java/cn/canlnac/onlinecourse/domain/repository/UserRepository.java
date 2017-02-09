package cn.canlnac.onlinecourse.domain.repository;

import java.util.Map;

import cn.canlnac.onlinecourse.domain.ChatList;
import cn.canlnac.onlinecourse.domain.CourseList;
import cn.canlnac.onlinecourse.domain.Follower;
import cn.canlnac.onlinecourse.domain.LearnRecordList;
import cn.canlnac.onlinecourse.domain.Login;
import cn.canlnac.onlinecourse.domain.Message;
import cn.canlnac.onlinecourse.domain.MessageList;
import cn.canlnac.onlinecourse.domain.Profile;
import rx.Observable;

/**
 * 用户接口.
 */
public interface UserRepository {
    /** 登录 */
    Observable<Login> login(String username, String password);
    /** 注册 */
    Observable<Integer> register(String username, String password);
    /** 获取用户资料 */
    Observable<Profile> getUserProfile(int userId);
    /** 更新个人资料 */
    Observable<Void> updateUserProfile(Map<String,String> profile);
    /** 获取个人消息 */
    Observable<MessageList> getMessages(Integer start, Integer count, Boolean isRead);
    /** 获取指定消息 */
    Observable<Message> getMessage(int messageId);
    /** 删除消息 */
    Observable<Void> deleteMessage(int messageId);
    /** 获取别人学习记录 */
    Observable<LearnRecordList> getOtherUserLearnRecord(int userId, Integer start, Integer count);
    /** 获取自己的粉丝 */
    Observable<Follower> getFollower(Integer start, Integer count);
    /** 获取用户的关注 */
    Observable<Follower> getUserFollowing(int userId, Integer start, Integer count);
    /** 关注用户 */
    Observable<Void> followUser(int userId);
    /** 取消关注 */
    Observable<Void> unfollowUser(int userId);

    /** 获取我的话题 */
    Observable<ChatList> getMyChats(Integer start, Integer count);

    /** 获取我收藏的话题 */
    Observable<ChatList> getMyFavoriteChats(Integer start, Integer count);

    /** 获取我收藏的课程 */
    Observable<CourseList> getMyFavoriteCourses(Integer start, Integer count);

    /** 获取登陆状态 */
    Observable<Login> getLogin();
    /** 设置登陆状态 */
    Observable<Void> setLogin(Login login);
}
