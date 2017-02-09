package cn.canlnac.onlinecourse.data.repository.datasource;

import android.support.annotation.Nullable;

import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.ChatListEntity;
import cn.canlnac.onlinecourse.data.entity.CommentListEntity;
import cn.canlnac.onlinecourse.data.entity.CourseListEntity;
import cn.canlnac.onlinecourse.data.entity.FollowerEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordListEntity;
import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.data.entity.MessageEntity;
import cn.canlnac.onlinecourse.data.entity.MessageListEntity;
import cn.canlnac.onlinecourse.data.entity.ProfileEntity;
import rx.Observable;

/**
 * 用户数据储存.
 */

public interface UserDataStore {
    /** 登录 */
    Observable<LoginEntity> login(String username, String password);
    /** 注册 */
    Observable<Integer> register(String username, String password);
    /** 获取用户资料 */
    Observable<ProfileEntity> getUserProfile(int userId);
    /** 更新个人资料 */
    Observable<Void> updateUserProfile(Map<String,String> profile);
    /** 获取个人消息 */
    Observable<MessageListEntity> getMessages(@Nullable Integer start, @Nullable Integer count, @Nullable Boolean isRead);
    /** 获取指定消息 */
    Observable<MessageEntity> getMessage(int messageId);
    /** 删除消息 */
    Observable<Void> deleteMessage(int messageId);
    /** 获取别人学习记录 */
    Observable<LearnRecordListEntity> getOtherUserLearnRecord(int userId, @Nullable Integer start, @Nullable Integer count);
    /** 获取自己的粉丝 */
    Observable<FollowerEntity> getFollower(@Nullable Integer start, @Nullable Integer count);
    /** 获取用户的关注 */
    Observable<FollowerEntity> getUserFollowing(int userId, @Nullable Integer start, @Nullable Integer count);
    /** 关注用户 */
    Observable<Void> followUser(int userId);
    /** 取消关注 */
    Observable<Void> unfollowUser(int userId);

    /** 获取我的话题 */
    Observable<ChatListEntity> getMyChats(Integer start, Integer count);

    /** 获取我收藏的话题 */
    Observable<ChatListEntity> getMyFavoriteChats(Integer start, Integer count);

    /** 获取我收藏的课程 */
    Observable<CourseListEntity> getMyFavoriteCourses(Integer start, Integer count);

    /** 获取我的回复 */
    Observable<CommentListEntity> getMyReplies(Integer start, Integer count);

    /** 获取登陆状态 */
    Observable<LoginEntity> getLogin();
    /** 设置登陆状态 */
    Observable<Void> setLogin(LoginEntity loginEntity);
}
