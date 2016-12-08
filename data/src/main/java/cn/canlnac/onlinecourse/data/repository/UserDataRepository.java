package cn.canlnac.onlinecourse.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.RegisterResponseEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStoreFactory;
import cn.canlnac.onlinecourse.domain.Register;
import cn.canlnac.onlinecourse.domain.Response;
import cn.canlnac.onlinecourse.domain.repository.UserRepository;
import rx.Observable;

/**
 * 用户数据接口.
 */
@Singleton
public class UserDataRepository implements UserRepository {
    private final UserDataStoreFactory userDataStoreFactory;
    private final RegisterResponseEntityDataMapper registerResponseEntityDataMapper;

    @Inject
    public UserDataRepository(
            UserDataStoreFactory dataStoreFactory,
            RegisterResponseEntityDataMapper registerResponseEntityDataMapper
    ) {
        this.userDataStoreFactory = dataStoreFactory;
        this.registerResponseEntityDataMapper = registerResponseEntityDataMapper;
    }

    @Override
    public Observable<Response<Register>> register(String username, String password, String email) {
        final UserDataStore userDataStore = this.userDataStoreFactory.createCloudDataStore();
        return userDataStore.register(username, password, email).map(registerResponseEntityDataMapper::transform);
    }
}
