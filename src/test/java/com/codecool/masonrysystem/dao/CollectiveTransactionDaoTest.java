package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.CollectiveTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CollectiveTransactionDaoTest {
    private Date sqlDate;
    private CollectiveTransactionDao collectiveTransactionDao;
    private CollectiveTransaction collectiveTransactionMock;

    @BeforeAll
    public void setUp(){
        sqlDate = new Date(2020, 10, 10);
        collectiveTransactionDao = new CollectiveTransactionDao();
        collectiveTransactionMock = mock(CollectiveTransaction.class);
        stub(collectiveTransactionMock.getId()).toReturn(999L);
        stub(collectiveTransactionMock.getTransactionId()).toReturn(11L);
        stub(collectiveTransactionMock.getUserId()).toReturn(12L);
        stub(collectiveTransactionMock.getDonationDate()).toReturn(sqlDate);
        stub(collectiveTransactionMock.getAmount()).toReturn(999);
    }

    @Test
    public void testIsCollectivePresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(999L);
        stub(resultSetMock.getLong("transaction_id")).toReturn(11L);
        stub(resultSetMock.getLong("user_id")).toReturn(12L);
        stub(resultSetMock.getDate("donation_date")).toReturn(sqlDate);
        stub(resultSetMock.getInt("amount")).toReturn(999);

        CollectiveTransaction collectiveTransaction = collectiveTransactionDao.create(resultSetMock);

        assertAll("Should return complete object",
                () -> assertEquals(collectiveTransactionMock.getId(), collectiveTransaction.getId()),
                () -> assertEquals(collectiveTransactionMock.getTransactionId(), collectiveTransaction.getTransactionId()),
                () -> assertEquals(collectiveTransactionMock.getUserId(), collectiveTransaction.getUserId()),
                () -> assertEquals(collectiveTransactionMock.getDonationDate(), collectiveTransaction.getDonationDate()),
                () -> assertEquals(collectiveTransactionMock.getAmount(), collectiveTransaction.getAmount())
        );
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotEquals(0, collectiveTransactionDao.getAll().size());
    }

    @Test
    public void testIsCollectiveInserting() throws SQLException {
        collectiveTransactionDao.insert(collectiveTransactionMock);
        CollectiveTransaction collectiveTransaction = collectiveTransactionDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(collectiveTransactionMock.getId(), collectiveTransaction.getId()),
                () -> assertEquals(collectiveTransactionMock.getTransactionId(), collectiveTransaction.getTransactionId()),
                () -> assertEquals(collectiveTransactionMock.getUserId(), collectiveTransaction.getUserId()),
                () -> assertEquals(collectiveTransactionMock.getDonationDate(), collectiveTransaction.getDonationDate()),
                () -> assertEquals(collectiveTransactionMock.getAmount(), collectiveTransaction.getAmount())
        );
        collectiveTransactionDao.delete(999L);
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        collectiveTransactionDao.insert(collectiveTransactionMock);
        assertEquals(999L, collectiveTransactionDao.getById(999L).getId());
        collectiveTransactionDao.delete(999L);
    }

    @Test
    public void testIsCollectiveUpdating() throws SQLException {
        CollectiveTransaction collectiveTransactionMockUpdated = mock(CollectiveTransaction.class);
        stub(collectiveTransactionMockUpdated.getId()).toReturn(999L);
        stub(collectiveTransactionMockUpdated.getTransactionId()).toReturn(11L);
        stub(collectiveTransactionMockUpdated.getUserId()).toReturn(12L);
        stub(collectiveTransactionMockUpdated.getDonationDate()).toReturn(sqlDate);
        stub(collectiveTransactionMockUpdated.getAmount()).toReturn(9999);

        collectiveTransactionDao.insert(collectiveTransactionMock);
        collectiveTransactionDao.update(collectiveTransactionMockUpdated);
        CollectiveTransaction collectiveTransaction = collectiveTransactionDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(collectiveTransactionMockUpdated.getId(), collectiveTransaction.getId()),
                () -> assertEquals(collectiveTransactionMockUpdated.getTransactionId(), collectiveTransaction.getTransactionId()),
                () -> assertEquals(collectiveTransactionMockUpdated.getUserId(), collectiveTransaction.getUserId()),
                () -> assertEquals(collectiveTransactionMockUpdated.getDonationDate(), collectiveTransaction.getDonationDate()),
                () -> assertEquals(collectiveTransactionMockUpdated.getAmount(), collectiveTransaction.getAmount())
        );
        collectiveTransactionDao.delete(999L);
    }

    @Test
    public void testIfCollectiveDeleting(){
        collectiveTransactionDao.insert(collectiveTransactionMock);
        collectiveTransactionDao.delete(999L);
        assertThrows(ElementNotFoundException.class, () -> collectiveTransactionDao.getById(999L));
    }
}