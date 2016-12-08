package cn.canlnac.onlinecourse.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import cn.canlnac.onlinecourse.data.ApplicationTestCase;
import cn.canlnac.onlinecourse.data.entity.UserEntity;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStoreFactory;
import rx.Observable;

import static org.mockito.BDDMockito.given;

/**
 * 用户储存测试用例.
 */

public class UserDataRepositoryTest extends ApplicationTestCase {
    private UserDataRepository userDataRepository;

    @Mock private UserDataStoreFactory mockUserDataStoreFactory;
    @Mock private UserDataStore mockUserDataStore;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userDataRepository = new UserDataRepository(mockUserDataStoreFactory);

        given(mockUserDataStoreFactory.createCloudDataStore()).willReturn(mockUserDataStore);
    }

    @Test
    public void testRegisterCase() {
    }

}
