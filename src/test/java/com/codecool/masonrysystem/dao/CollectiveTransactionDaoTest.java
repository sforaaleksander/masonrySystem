package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Artifact;
import com.codecool.masonrysystem.model.CollectiveTransaction;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

class CollectiveTransactionDaoTest {

    private Date sqlDate = new Date(2020, 10, 10);
    private CollectiveTransactionDao collectiveTransactionDao = new CollectiveTransactionDao();

    @Test
    public void testIsCollectivePresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(1L);
        stub(resultSetMock.getLong("transaction_id")).toReturn(1L);
        stub(resultSetMock.getLong("user_id")).toReturn(1L);
        stub(resultSetMock.getDate("donation_date")).toReturn(sqlDate);
        stub(resultSetMock.getInt("amount")).toReturn(10);

        CollectiveTransaction collectiveTransactionExpected = new CollectiveTransaction();
        collectiveTransactionExpected.setId(1L);
        collectiveTransactionExpected.setTransactionId(1L);
        collectiveTransactionExpected.setUserId(1L);
        collectiveTransactionExpected.setDonationDate(sqlDate);
        collectiveTransactionExpected.setAmount(10);

        CollectiveTransaction collectiveTransaction = collectiveTransactionDao.create(resultSetMock);

        assertAll("Should return complete object",
                () -> assertEquals(collectiveTransactionExpected.getId(), collectiveTransaction.getId()),
                () -> assertEquals(collectiveTransactionExpected.getTransactionId(), collectiveTransaction.getTransactionId()),
                () -> assertEquals(collectiveTransactionExpected.getUserId(), collectiveTransaction.getUserId()),
                () -> assertEquals(collectiveTransactionExpected.getDonationDate(), collectiveTransaction.getDonationDate()),
                () -> assertEquals(collectiveTransactionExpected.getAmount(), collectiveTransaction.getAmount())
        );
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotEquals(0, collectiveTransactionDao.getAll().size());
    }

    @Test
    public void testIsCollectiveInserting() throws SQLException {
        CollectiveTransaction collectiveTransactionMock = mock(CollectiveTransaction.class);
        stub(collectiveTransactionMock.getId()).toReturn(999L);
        stub(collectiveTransactionMock.getTransactionId()).toReturn(10L);
        stub(collectiveTransactionMock.getUserId()).toReturn(12L);
        stub(collectiveTransactionMock.getDonationDate()).toReturn(sqlDate);
        stub(collectiveTransactionMock.getAmount()).toReturn(999);
        collectiveTransactionDao.insert(collectiveTransactionMock);
        CollectiveTransaction collectiveTransaction = collectiveTransactionDao.getById(999L);
        assertAll("Should return complete object",
                () -> assertEquals(collectiveTransactionMock.getId(), collectiveTransaction.getId()),
                () -> assertEquals(collectiveTransactionMock.getTransactionId(), collectiveTransaction.getTransactionId()),
                () -> assertEquals(collectiveTransactionMock.getUserId(), collectiveTransaction.getUserId()),
                () -> assertEquals(collectiveTransactionMock.getDonationDate(), collectiveTransaction.getDonationDate()),
                () -> assertEquals(collectiveTransactionMock.getAmount(), collectiveTransaction.getAmount())
        );
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        assertEquals(999L, collectiveTransactionDao.getById(999L).getId());
    }

    @Test
    public void testIsCollectiveUpdating() throws SQLException {
        CollectiveTransaction collectiveTransactionMock = mock(CollectiveTransaction.class);
        stub(collectiveTransactionMock.getId()).toReturn(999L);
        stub(collectiveTransactionMock.getTransactionId()).toReturn(10L);
        stub(collectiveTransactionMock.getUserId()).toReturn(12L);
        stub(collectiveTransactionMock.getDonationDate()).toReturn(sqlDate);
        stub(collectiveTransactionMock.getAmount()).toReturn(9999);
        collectiveTransactionDao.update(collectiveTransactionMock);
        CollectiveTransaction collectiveTransaction = collectiveTransactionDao.getById(999L);
        assertAll("Should return complete object",
                () -> assertEquals(collectiveTransactionMock.getId(), collectiveTransaction.getId()),
                () -> assertEquals(collectiveTransactionMock.getTransactionId(), collectiveTransaction.getTransactionId()),
                () -> assertEquals(collectiveTransactionMock.getUserId(), collectiveTransaction.getUserId()),
                () -> assertEquals(collectiveTransactionMock.getDonationDate(), collectiveTransaction.getDonationDate()),
                () -> assertEquals(collectiveTransactionMock.getAmount(), collectiveTransaction.getAmount())
        );
    }

    @Test
    public void testIfCollectiveDeleting(){
        collectiveTransactionDao.delete(999L);
        assertThrows(ElementNotFoundException.class, () -> collectiveTransactionDao.getById(999L));
    }
}