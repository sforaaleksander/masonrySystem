package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Apprentice;
import com.codecool.masonrysystem.model.Rank;
import com.codecool.masonrysystem.model.User;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDaoTest {
    private UserDao userDao = new UserDao();

    @Test
    public void testIsUserPresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(1L);
        stub(resultSetMock.getString("first_name")).toReturn("first_name");
        stub(resultSetMock.getString("last_name")).toReturn("last_name");
        stub(resultSetMock.getString("email")).toReturn("email");
        stub(resultSetMock.getString("password")).toReturn("password");
        stub(resultSetMock.getBoolean("is_active")).toReturn(true);


        User userExpected = new Apprentice();
        userExpected.setId(1L);
        userExpected.setFirstName("first_name");
        userExpected.setLastName("last_name");
        userExpected.setEmail("email");
        userExpected.setPassword("password");
        userExpected.setIsActive(true);

        User user = userDao.create(resultSetMock);

        assertAll("Should return complete object",
                () -> assertEquals(userExpected.getId(), user.getId()),
                () -> assertEquals(userExpected.getFirstName(), user.getFirstName()),
                () -> assertEquals(userExpected.getLastName(), user.getLastName()),
                () -> assertEquals(userExpected.getEmail(), user.getEmail()),
                () -> assertEquals(userExpected.getPassword(), user.getPassword()),
                () -> assertEquals(userExpected.getIsActive(), user.getIsActive())
        );
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotNull(userDao.getAll());
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        assertNotNull(userDao.getById(1L));
    }

    @Test
    public void testIsUserInserting() throws SQLException {
        User userSpy = mock(User.class);
        stub(userSpy.getId()).toReturn(999L);
        stub(userSpy.getFirstName()).toReturn("First Name");
        stub(userSpy.getLastName()).toReturn("Last Name");
        stub(userSpy.getEmail()).toReturn("email@email.com");
        stub(userSpy.getPassword()).toReturn("password");
        stub(userSpy.getRank()).toReturn(Rank.AINSOPHAUR);
        stub(userSpy.getIsActive()).toReturn(true);
        assertTrue(userDao.insert(userSpy));
    }

    @Test
    public void testIsUserUpdating() throws SQLException {
        User userSpy = mock(User.class);
        stub(userSpy.getId()).toReturn(999L);
        stub(userSpy.getFirstName()).toReturn("Updated First Name");
        stub(userSpy.getLastName()).toReturn("Updated Last Name");
        stub(userSpy.getEmail()).toReturn("email@email.com");
        stub(userSpy.getPassword()).toReturn("password");
        stub(userSpy.getRank()).toReturn(Rank.AINSOPHAUR);
        stub(userSpy.getIsActive()).toReturn(true);
        assertTrue(userDao.update(userSpy));
    }

    @Test
    public void testIsUserPresentByEmail() throws SQLException {
        assertNotNull(userDao.getUserByEmail("email@email.com"));
    }

    @Test
    public void testIsUserDeleting(){
        assertTrue(userDao.delete(999L));
    }

    @Test
    public void testAreUsersPresentByRole() throws SQLException {
        assertNotNull(userDao.getUsersByRole(3));
    }

    @Test
    public void testExceptionWhereNoUsersFoundByRole() {
        assertThrows(ElementNotFoundException.class, () -> userDao.getUsersByRole(999));
    }


}