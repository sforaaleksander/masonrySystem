package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.model.Journeyman;
import com.codecool.masonrysystem.model.Lodge;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LodgeDaoTest {

    private LodgeDao lodgeDaoWithMock;
    private Journeyman journeymanMock;
    private ResultSet resultSetMock;
    private Lodge expectedLodge;
    private Lodge lodge;
    private static LodgeDao lodgeDao;

    @BeforeAll
    public void setUp() throws SQLException {
        lodgeDao = new LodgeDao();
        UserDao userDaoMock = mock(UserDao.class);
        resultSetMock = mock(ResultSet.class);
        lodgeDaoWithMock = new LodgeDao(userDaoMock);
        journeymanMock = mock(Journeyman.class, RETURNS_MOCKS);
        when(userDaoMock.getById(any())).thenReturn(journeymanMock);
        when(resultSetMock.getLong("id")).thenReturn(0L);
        when(resultSetMock.getString("name")).thenReturn("name");
        createExpectedLodge();
        createLodgeFromResultSet();
    }

    private void createExpectedLodge() {
        expectedLodge = new Lodge();
        expectedLodge.setId(0L);
        expectedLodge.setName("name");
        expectedLodge.setOwner(journeymanMock);
    }

    private void createLodgeFromResultSet() throws SQLException {
        lodge = lodgeDaoWithMock.create(resultSetMock);
    }

    @Test
    public void testIfIdsAreSameInExpectedAndCreatedLodge() {
        assertAll("Check if expectedLodge consistent with lodge",
                () -> assertEquals(expectedLodge.getId(), lodge.getId()),
                () -> assertEquals(expectedLodge.getName(), lodge.getName()),
                () -> assertEquals(expectedLodge.getOwner(), lodge.getOwner()
                ));
    }

    @Test
    public void testIfAllElementsArePresent() throws SQLException {
        assertNotNull(lodgeDao.getAll());
    }
    @Test
    public void testIfElementByIdIsPresent() throws SQLException {
        assertNotNull(lodgeDao.getById(1L));
    }
}