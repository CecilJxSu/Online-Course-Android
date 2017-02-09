package cn.canlnac.onlinecourse.data.repository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cn.canlnac.onlinecourse.data.ApplicationTestCase;
import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import cn.canlnac.onlinecourse.data.entity.mapper.ChatListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.CourseListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.FollowerEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.LearnRecordListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.LoginEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.MessageEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.MessageListEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.ProfileEntityDataMapper;
import cn.canlnac.onlinecourse.data.entity.mapper.UserEntityDataMapper;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStore;
import cn.canlnac.onlinecourse.data.repository.datasource.UserDataStoreFactory;
import rx.Observable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * 用户储存测试用例.
 */

public class UserDataRepositoryTest extends ApplicationTestCase {
    private UserDataRepository userDataRepository;

    @Mock private UserDataStoreFactory mockUserDataStoreFactory;
    @Mock private UserDataStore mockUserDataStore;
    @Mock private UserEntityDataMapper mockUserEntityDataMapper;


    @Mock private LoginEntityDataMapper mockLoginEntityDataMapper;
    @Mock private ProfileEntityDataMapper mockProfileEntityDataMapper;
    @Mock private MessageListEntityDataMapper mockMessageListEntityDataMapper;
    @Mock private MessageEntityDataMapper mockMessageEntityDataMapper;
    @Mock private LearnRecordListEntityDataMapper mockLearnRecordListEntityDataMapper;
    @Mock private FollowerEntityDataMapper messageFollowerEntityDataMapper;
    @Mock private ChatListEntityDataMapper chatListEntityDataMapper;
    @Mock private CourseListEntityDataMapper courseListEntityDataMapper;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userDataRepository = new UserDataRepository(
                mockUserDataStoreFactory,
                mockLoginEntityDataMapper,
                mockProfileEntityDataMapper,
                mockMessageListEntityDataMapper,
                mockMessageEntityDataMapper,
                mockLearnRecordListEntityDataMapper,
                messageFollowerEntityDataMapper,
                chatListEntityDataMapper,
                courseListEntityDataMapper
        );
        given(mockUserDataStoreFactory.create()).willReturn(mockUserDataStore);
    }

    /**
     * 测试注册
     */
    @Test
    public void testUserCase() {
        RegisterEntity registerEntity = new RegisterEntity();
        registerEntity.setUserId(1);

        given(mockUserDataStore.register("username", "password")).willReturn(Observable.just(1));
        userDataRepository.register("username", "password");
        verify(mockUserDataStoreFactory).create();
        verify(mockUserDataStore).register("username", "password");
    }

}
