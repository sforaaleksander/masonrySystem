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
        assertNotEquals(0, userDao.getAll().size());
    }

    @Test
    public void testIsUserInserting() throws SQLException {
        Apprentice userMock = mock(Apprentice.class);
        stub(userMock.getId()).toReturn(999L);
        stub(userMock.getFirstName()).toReturn("First Name");
        stub(userMock.getLastName()).toReturn("Last Name");
        stub(userMock.getEmail()).toReturn("email@email.com");
        stub(userMock.getPassword()).toReturn("password");
        stub(userMock.getRank()).toReturn(Rank.AINSOPHAUR);
        stub(userMock.getIsActive()).toReturn(true);
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
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        assertNotNull(userDao.getById(1L));
    }


    @Test
    public void testIsUserUpdating() throws SQLException {
        User userMock = mock(User.class);
        stub(userMock.getId()).toReturn(999L);
        stub(userMock.getFirstName()).toReturn("Updated First Name");
        stub(userMock.getLastName()).toReturn("Updated Last Name");
        stub(userMock.getEmail()).toReturn("email@email.com");
        stub(userMock.getPassword()).toReturn("password");
        stub(userMock.getRank()).toReturn(Rank.AINSOPHAUR);
        stub(userMock.getIsActive()).toReturn(true);
        userDao.update(userMock);
        User user = userDao.getById(999L);
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
    public void testIsUserPresentByEmail() throws SQLException {
        assertEquals("email@email.com", userDao.getUserByEmail("email@email.com").getEmail());
    }

    @Test
    public void testIsUserDeleting(){
        userDao.delete(999L);
        assertThrows(ElementNotFoundException.class, () -> userDao.getById(999L));
    }

    @Test
    public void testAreUsersPresentByRole() throws SQLException {
        assertNotEquals(0, userDao.getUsersByRole(3).size());
    }

}