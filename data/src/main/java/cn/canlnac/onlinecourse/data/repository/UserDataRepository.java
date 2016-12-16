package cn.canlnac.onlinecourse.data.repository;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.FollowerEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.LearnRecordListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.LoginEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.MessageEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.MessageListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.ProfileEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.RegisterEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStoreFactory;
import cn.canlnac.onlinecourse.domain.Follower;
import cn.canlnac.onlinecourse.domain.LearnRecordList;
import cn.canlnac.onlinecourse.domain.Login;
import cn.canlnac.onlinecourse.domain.Message;
import cn.canlnac.onlinecourse.domain.MessageList;
import cn.canlnac.onlinecourse.domain.Profile;
import cn.canlnac.onlinecourse.domain.Register;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 用户数据接口，提供给domain调用.
 */
@Singleton
public class UserDataRepository implements UserRepository {
    private final UserDataStore userDataStore;

    private final LoginEntityDataMapper loginEntityDataMapper;
    private final RegisterEntityDataMapper registerEntityDataMapper;
    private final ProfileEntityDataMapper profileEntityDataMapper;
    private final MessageListEntityDataMapper messageListEntityDataMapper;
    private final MessageEntityDataMapper messageEntityDataMapper;
    private final LearnRecordListEntityDataMapper learnRecordListEntityDataMapper;
    private final FollowerEntityDataMapper followerEntityDataMapper;

    @Inject
    public UserDataRepository(
            UserDataStoreFactory userDataStoreFactory,
            LoginEntityDataMapper loginEntityDataMapper,
            RegisterEntityDataMapper registerEntityDataMapper,
            ProfileEntityDataMapper profileEntityDataMapper,
            MessageListEntityDataMapper messageListEntityDataMapper,
            MessageEntityDataMapper messageEntityDataMapper,
            LearnRecordListEntityDataMapper learnRecordListEntityDataMapper,
            FollowerEntityDataMapper followerEntityDataMapper
    ) {
        this.userDataStore = userDataStoreFactory.create();
        this.loginEntityDataMapper = loginEntityDataMapper;
        this.registerEntityDataMapper = registerEntityDataMapper;
        this.profileEntityDataMapper = profileEntityDataMapper;
        this.messageListEntityDataMapper = messageListEntityDataMapper;
        this.messageEntityDataMapper = messageEntityDataMapper;
        this.learnRecordListEntityDataMapper = learnRecordListEntityDataMapper;
        this.followerEntityDataMapper = followerEntityDataMapper;
    }

    @Override
    public Observable<Login> login(String username, String password) {
        return userDataStore.login(username,password).map(loginEntityDataMapper::transform);
    }

    @Override
    public Observable<Register> register(String username, String password) {
        return userDataStore.register(username,password).map(registerEntityDataMapper::transform);
    }

    @Override
    public Observable<Profile> getUserProfile(int userId) {
        return userDataStore.getUserProfile(userId).map(profileEntityDataMapper::transform);
    }

    @Override
    public Observable<Void> updateUserProfile(Map<String, String> profile) {
        return userDataStore.updateUserProfile(profile);
    }

    @Override
    public Observable<MessageList> getMessages(Integer start, Integer count, Boolean isRead) {
        return userDataStore.getMessages(start,count,isRead).map(messageListEntityDataMapper::transform);
    }

    @Override
    public Observable<Message> getMessage(int messageId) {
        return userDataStore.getMessage(messageId).map(messageEntityDataMapper::transform);
    }

    @Override
    public Observable<Void> deleteMessage(int messageId) {
        return userDataStore.deleteMessage(messageId);
    }

    @Override
    public Observable<LearnRecordList> getOtherUserLearnRecord(int userId, Integer start, Integer count) {
        return userDataStore.getOtherUserLearnRecord(userId,start,count).map(learnRecordListEntityDataMapper::transform);
    }

    @Override
    public Observable<Follower> getFollower(Integer start, Integer count) {
        return userDataStore.getFollower(start,count).map(followerEntityDataMapper::transform);
    }

    @Override
    public Observable<Follower> getUserFollowing(int userId, Integer start, Integer count) {
        return userDataStore.getUserFollowing(userId,start,count).map(followerEntityDataMapper::transform);
    }

    @Override
    public Observable<Void> followUser(int userId) {
        return userDataStore.followUser(userId);
    }

    @Override
    public Observable<Void> unfollowUser(int userId) {
        return userDataStore.unfollowUser(userId);
    }
}