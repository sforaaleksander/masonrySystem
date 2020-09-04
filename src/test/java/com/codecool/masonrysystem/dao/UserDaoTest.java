package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Rank;
import com.codecool.masonrysystem.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoTest {
    private UserDao userDao;
    private User userMock;

    @BeforeAll
    public void setUp(){
        userDao = new UserDao();
        userMock = mock(User.class);
        stub(userMock.getId()).toReturn(999L);
        stub(userMock.getFirstName()).toReturn("TestFirstName");
        stub(userMock.getLastName()).toReturn("TestLastName");
        stub(userMock.getEmail()).toReturn("email@email.com");
        stub(userMock.getPassword()).toReturn("password");
        stub(userMock.getRank()).toReturn(Rank.AINSOPHAUR);
        stub(userMock.getIsActive()).toReturn(true);
    }

    @Test
    public void testIsUserPresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(999L);
        stub(resultSetMock.getString("first_name")).toReturn("TestFirstName");
        stub(resultSetMock.getString("last_name")).toReturn("TestLastName");
        stub(resultSetMock.getString("email")).toReturn("email@email.com");
        stub(resultSetMock.getString("password")).toReturn("password");
        stub(resultSetMock.getBoolean("is_active")).toReturn(true);

        User user = userDao.create(resultSetMock);

        assertAll("Should return complete object",
                () -> assertEquals(userMock.getId(), user.getId()),
                () -> assertEquals(userMock.getFirstName(), user.getFirstName()),
                () -> assertEquals(userMock.getLastName(), user.getLastName()),
                () -> assertEquals(userMock.getEmail(), user.getEmail()),
                () -> assertEquals(userMock.getPassword(), user.getPassword()),
                () -> assertEquals(userMock.getIsActive(), user.getIsActive())
        );
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotEquals(0, userDao.getAll().size());
    }

    @Test
    public void testIsUserInserting() throws SQLException {
        userDao.insert(userMock);
        User user = userDao.getById(999L);
        assertAll("Should return complete object",
                () -> assertEquals(userMock.getId(), user.getId()),
                () -> assertEquals(userMock.getFirstName(), user.getFirstName()),
                () -> assertEquals(userMock.getLastName(), user.getLastName()),
                () -> assertEquals(userMock.getEmail(), user.getEmail()),
                () -> assertEquals(userMock.getPassword(), user.getPassword()),
                () -> assertEquals(userMock.getIsActive(), user.getIsActive())
        );
        userDao.delete(999L);
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        userDao.insert(userMock);
        assertNotNull(userDao.getById(999L));
        userDao.delete(999L);
    }


    @Test
    public void testIsUserUpdating() throws SQLException {
        User userMockUpdated = mock(User.class);
        stub(userMockUpdated.getId()).toReturn(999L);
        stub(userMockUpdated.getFirstName()).toReturn("Updated First Name");
        stub(userMockUpdated.getLastName()).toReturn("Updated Last Name");
        stub(userMockUpdated.getEmail()).toReturn("email@email.com");
        stub(userMockUpdated.getPassword()).toReturn("password");
        stub(userMockUpdated.getRank()).toReturn(Rank.THEILLUMINATI);
        stub(userMockUpdated.getIsActive()).toReturn(true);

        userDao.insert(userMock);
        userDao.update(userMockUpdated);
        User user = userDao.getById(999L);
        assertAll("Should return complete object",
                () -> assertEquals(userMockUpdated.getId(), user.getId()),
                () -> assertEquals(userMockUpdated.getFirstName(), user.getFirstName()),
                () -> assertEquals(userMockUpdated.getLastName(), user.getLastName()),
                () -> assertEquals(userMockUpdated.getEmail(), user.getEmail()),
                () -> assertEquals(userMockUpdated.getPassword(), user.getPassword()),
                () -> assertEquals(userMockUpdated.getIsActive(), user.getIsActive())
        );
        userDao.delete(999L);
    }

    @Test
    public void testIsUserPresentByEmail() throws SQLException {
        userDao.insert(userMock);
        assertEquals("email@email.com", userDao.getUserByEmail("email@email.com").getEmail());
        userDao.delete(999L);
    }

    @Test
    public void testIsUserDeleting(){
        userDao.insert(userMock);
        userDao.delete(999L);
        assertThrows(ElementNotFoundException.class, () -> userDao.getById(999L));
    }

    @Test
    public void testAreUsersPresentByRole() throws SQLException {
        assertNotEquals(0, userDao.getUsersByRole(3).size());
    }

}