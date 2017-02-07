package cn.canlnac.onlinecourse.data.repository.datasource;

import android.support.annotation.Nullable;

import java.util.Map;

import cn.canlnac.onlinecourse.data.entity.FollowerEntity;
import cn.canlnac.onlinecourse.data.entity.LearnRecordListEntity;
import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.data.entity.MessageEntity;
import cn.canlnac.onlinecourse.data.entity.MessageListEntity;
import cn.canlnac.onlinecourse.data.entity.ProfileEntity;
import cn.canlnac.onlinecourse.data.net.RestApi;
import rx.Observable;

/**
 * 从服务器中获取用户数据.
 */

public class CloudUserDataStore implements UserDataStore {
    private final RestApi restApi;

    public CloudUserDataStore(RestApi restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<LoginEntity> login(String username, String password) {
        return this.restApi.login(username, password);
    }

    @Override
    public Observable<Integer> register(String username, String password) {
        return this.restApi.register(username, password);
    }

    @Override
    public Observable<ProfileEntity> getUserProfile(int userId) {
        return this.restApi.getUserProfile(userId);
    }

    @Override
    public Observable<Void> updateUserProfile(Map<String, String> profile) {
        return this.restApi.updateUserProfile(profile);
    }

    @Override
    public Observable<MessageListEntity> getMessages(@Nullable Integer start, @Nullable Integer count, @Nullable Boolean isRead) {
        return this.restApi.getMessages(start,count,isRead);
    }

    @Override
    public Observable<MessageEntity> getMessage(int messageId) {
        return this.restApi.getMessage(messageId);
    }

    @Override
    public Observable<Void> deleteMessage(int messageId) {
        return this.restApi.deleteMessage(messageId);
    }

    @Override
    public Observable<LearnRecordListEntity> getOtherUserLearnRecord(int userId, @Nullable Integer start, @Nullable Integer count) {
        return this.restApi.getOtherUserLearnRecord(userId, start, count);
    }

    @Override
    public Observable<FollowerEntity> getFollower(@Nullable Integer start, @Nullable Integer count) {
        return this.restApi.getFollower(start, count);
    }

    @Override
    public Observable<FollowerEntity> getUserFollowing(int userId, @Nullable Integer start, @Nullable Integer count) {
        return this.restApi.getUserFollowing(userId, start, count);
    }

    @Override
    public Observable<Void> followUser(int userId) {
        return this.restApi.followUser(userId);
    }

    @Override
    public Observable<Void> unfollowUser(int userId) {
        return this.restApi.unfollowUser(userId);
    }

    @Override
    public Observable<LoginEntity> getLogin() {
        return this.restApi.getLogin();
    }

    @Override
    public Observable<Void> setLogin(LoginEntity loginEntity) {
        return this.restApi.setLogin(loginEntity);
    }
}
