package com.online_market;

import com.online_market.dao.UserDao;
import com.online_market.dao.UserDaoImpl;
import com.online_market.entity.User;
import com.online_market.entity.enums.Roles;
import com.online_market.service.UserService;
import com.online_market.service.UserServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for testing {@link UserServiceImpl}
 *
 * @author Siarhei
 * @version 1.0
 */
public class UserServiceTest {

    @Mock
    private UserDaoImpl userDaoMock;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllUsers_ListOfUsers() {

        //expected
        List expected = new ArrayList();
        expected.add(new User());
        expected.add(new User());
        //mock
        when(userDaoMock.getAll("User")).thenReturn(expected);

        //call
        List<User> actual = userService.findAll();

        //assert
        assertEquals(actual, expected);
    }

    @Test
    public void testGettingUserById_returnsUser() {

        //expected
        int id = 8;
        User expected = new User();
        expected.setId(id);
        //mock
        when(userDaoMock.getById(User.class, id)).thenReturn(expected);

        //call
        User actual = userService.getById(id);

        //assert
        assertEquals(actual, expected);
    }


    @Test
    public void testGettingUserById_returnsNullUser() {

        //expected
        int id = 1;
        User expected = null;
        //mock
        when(userDaoMock.getById(User.class, id)).thenReturn(expected);

        //call
        User actual = userService.getById(id);

        //assert
        assertEquals(actual, expected);
    }


    @Test
    public void testUpdate_void() {

        //expected
        int id = 8;
        User expected = new User();
        expected.setId(id);
        expected.setPassword("1");
        expected.setRole(Roles.ADMIN);
        //mock
        when(userDaoMock.getById(User.class, id)).thenReturn(expected);

        User user = new User();
        user.setId(8);
        userService.update(user);

        verify(userDaoMock).update(any(User.class));

        verify(userDaoMock).getById(User.class, 8);
    }

    @Test
    public void testValidate_returnsUser() {

        //expected
        String expectedLogin = "login";
        String expectedPassword = "password";
        User expected = new User();
        expected.setLogin(expectedLogin);
        expected.setPassword(expectedPassword);
        //mock
        when(userDaoMock.validate(expectedLogin, expectedPassword)).thenReturn(expected);

        //call
        User actual = userService.validate(expectedLogin, expectedPassword);

        //assert
        assertEquals(actual, expected);
    }

    @Test
    public void testValidate_returnsNullUser() {

        //expected
        String expectedLogin = "";
        String expectedPassword = "password";
        User expected = null;
        //mock
        when(userDaoMock.validate(expectedLogin, expectedPassword)).thenReturn(expected);

        //call
        User actual = userService.validate(expectedLogin, expectedPassword);

        //assert
        assertEquals(actual, expected);
    }


    @Test
    public void testRegisterUser_void() {

        userService.register(new User());

        verify(userDaoMock).register(any(User.class));

    }

    @Test
    public void testLogout_void() {

        //expected
        List<User> expected = new ArrayList<>();

        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user1.setAuth(false);
        user2.setId(2);
        user2.setAuth(false);

        expected.add(user1);
        expected.add(user2);

        //mock
        when(userDaoMock.getAll("User")).thenReturn(expected);
        when(userDaoMock.getById(User.class, 1)).thenReturn(user1);

        //actual
        user1.setAuth(true);
        userService.logout();

        assertEquals(user1.isAuth(), user2.isAuth());
    }

    @Test
    public void testAuthorizeById_void() {

        //expected
        boolean expected = true;

        //mock
        User user = new User();
        user.setId(1);
        user.setAuth(false);
        when(userDaoMock.getById(User.class, user.getId())).thenReturn(user);

        //actual

        userService.authorize(user.getId());

        //assert
        assertEquals(expected, user.isAuth());
    }

    @Test
    public void testGetAuthorizedUserId_returnsUserId() {

        //expected
        int expected = 1;

        //mock
        when(userDaoMock.getAuthirizedUserId()).thenReturn(expected);

        //actual
        int actual = userService.getAuthorizedUserId();

        //assert
        assertEquals(expected, actual);
    }

}
