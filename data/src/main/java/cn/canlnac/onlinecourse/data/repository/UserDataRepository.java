package cn.canlnac.onlinecourse.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.ResponseEntity;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStoreFactory;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 用户数据接口.
 */
@Singleton
public class UserDataRepository implements UserRepository {
    private final UserDataStoreFactory userDataStoreFactory;

    @Inject
    public UserDataRepository(UserDataStoreFactory dataStoreFactory) {
        this.userDataStoreFactory = dataStoreFactory;
    }

    @Override
    public Observable<ResponseEntity> register(String username, String password, String email) {
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.register(username, password, email);
    }
}
