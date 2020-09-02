package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Transaction;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

class TransactionDaoTest {
    private Date sqlDate = mock(java.sql.Date.class);
    private TransactionDao transactionDao = new TransactionDao();

    @Test
    public void testIsTransactionPresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(1L);
        stub(resultSetMock.getLong("user_id")).toReturn(1L);
        stub(resultSetMock.getLong("artifact_id")).toReturn(1L);
        stub(resultSetMock.getDate("open_transaction")).toReturn(sqlDate);
        stub(resultSetMock.getDate("close_transaction")).toReturn(sqlDate);

        Transaction transactionExpected = new Transaction();
        transactionExpected.setId(1L);
        transactionExpected.setUserId(1L);
        transactionExpected.setArtifactId(1L);
        transactionExpected.setOpenTransaction(sqlDate);
        transactionExpected.setCloseTransaction(sqlDate);

        Transaction transaction = transactionDao.create(resultSetMock);

        assertAll("Check if db consistent with object",
                () -> assertEquals(transactionExpected.getId(), transaction.getId()),
                () -> assertEquals(transactionExpected.getUserId(), transaction.getUserId()),
                () -> assertEquals(transactionExpected.getArtifactId(), transaction.getArtifactId()),
                () -> assertEquals(transactionExpected.getOpenTransaction(), transaction.getOpenTransaction()),
                () -> assertEquals(transactionExpected.getCloseTransaction(), transaction.getCloseTransaction())
        );
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotEquals(0, transactionDao.getAll().size());
    }

    @Test
    public void testIsTransactionInserting() throws SQLException {
        Transaction transactionMock = mock(Transaction.class);
        stub(transactionMock.getId()).toReturn(999L);
        stub(transactionMock.getUserId()).toReturn(12L);
        stub(transactionMock.getArtifactId()).toReturn(2L);
        stub(transactionMock.getOpenTransaction()).toReturn(sqlDate);
        stub(transactionMock.getCloseTransaction()).toReturn(sqlDate);
        transactionDao.insert(transactionMock);
        Transaction transaction = transactionDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(transactionMock.getId(), transaction.getId()),
                () -> assertEquals(transactionMock.getUserId(), transaction.getUserId()),
                () -> assertEquals(transactionMock.getArtifactId(), transaction.getArtifactId()),
                () -> assertEquals(transactionMock.getOpenTransaction(), transaction.getOpenTransaction()),
                () -> assertEquals(transactionMock.getCloseTransaction(), transaction.getCloseTransaction())
        );
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        assertNotNull(transactionDao.getById(999L));
    }


    @Test
    public void testIsTransactionUpdating() throws SQLException {
        Transaction transactionMock = mock(Transaction.class);
        stub(transactionMock.getId()).toReturn(999L);
        stub(transactionMock.getUserId()).toReturn(12L);
        stub(transactionMock.getArtifactId()).toReturn(8L);
        stub(transactionMock.getOpenTransaction()).toReturn(sqlDate);
        stub(transactionMock.getCloseTransaction()).toReturn(sqlDate);
        transactionDao.update(transactionMock);
        Transaction transaction = transactionDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(transactionMock.getId(), transaction.getId()),
                () -> assertEquals(transactionMock.getUserId(), transaction.getUserId()),
                () -> assertEquals(transactionMock.getArtifactId(), transaction.getArtifactId()),
                () -> assertEquals(transactionMock.getOpenTransaction(), transaction.getOpenTransaction()),
                () -> assertEquals(transactionMock.getCloseTransaction(), transaction.getCloseTransaction())
        );
    }

    @Test
    public void testIsTransactionDeleting(){
        transactionDao.delete(999L);
        assertThrows(ElementNotFoundException.class, () -> transactionDao.getById(999L));
    }
}