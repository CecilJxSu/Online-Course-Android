package cn.canlnac.onlinecourse.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cn.canlnac.onlinecourse.data.ApplicationTestCase;
import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import cn.canlnac.onlinecourse.data.entity.mapper.RegisterEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.RegisterDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.RegisterDataStoreFactory;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * 用户储存测试用例.
 */

public class RegisterDataRepositoryTest extends ApplicationTestCase {
    private RegisterDataRepository registerDataRepository;

    @Mock private RegisterDataStoreFactory mockRegisterDataStoreFactory;
    @Mock private RegisterDataStore mockRegisterDataStore;
    @Mock private RegisterEntityDataMapper mockRegisterEntityDataMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        registerDataRepository = new RegisterDataRepository(mockRegisterDataStoreFactory,mockRegisterEntityDataMapper);
        given(mockRegisterDataStoreFactory.create()).willReturn(mockRegisterDataStore);
    }

    /**
     * 测试注册
     */
    @Test
    public void testRegisterCase() {
        RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setUserId(1);

        given(mockRegisterDataStore.register("username", "password")).willReturn(Observable.just(registerEntity));
        registerDataRepository.register("username", "password");
        verify(mockRegisterDataStoreFactory).create();
        verify(mockRegisterDataStore).register("username", "password");
    }

}
