package cn.canlnac.onlinecourse.data.repository.datasource;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.mapper.RegisterEntityDataMapper;
import cn.canlnac.onlinecourse.domain.Register;
import cn.canlnac.onlinecourse.domain.repository.RegisterRepository;
import rx.Observable;

/**
 * 用户数据接口，提供给domain调用.
 */
@Singleton
public class UserDataRepository implements RegisterRepository {
    private final RegisterDataStoreFactory registerDataStoreFactory;
    private final RegisterEntityDataMapper registerEntityDataMapper;

    @Inject
    public UserDataRepository(
            RegisterDataStoreFactory registerDataStoreFactory,
            RegisterEntityDataMapper registerEntityDataMapper
    ) {
        this.registerDataStoreFactory = registerDataStoreFactory;
        this.registerEntityDataMapper = registerEntityDataMapper;
    }

    @Override
    public Observable<Register> register(String username, String password) {
        final RegisterDataStore registerDataStore = this.registerDataStoreFactory.create();
        return registerDataStore.register(username, password).map(registerEntityDataMapper::transform);
    }
}
