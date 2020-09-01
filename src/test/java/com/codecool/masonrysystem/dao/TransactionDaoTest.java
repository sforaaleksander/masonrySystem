package com.codecool.masonrysystem.dao;

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

        assertEquals(transactionExpected.getId(), transaction.getId());
        assertEquals(transactionExpected.getUserId(), transaction.getUserId());
        assertEquals(transactionExpected.getArtifactId(), transaction.getArtifactId());
        assertEquals(transactionExpected.getOpenTransaction(), transaction.getOpenTransaction());
        assertEquals(transactionExpected.getCloseTransaction(), transaction.getCloseTransaction());
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotNull(transactionDao.getAll());
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        assertNotNull(transactionDao.getById(1L));
    }

    @Test
    public void testIsTransactionInserting(){
        Transaction transaction = mock(Transaction.class);
        stub(transaction.getId()).toReturn(999L);
        stub(transaction.getUserId()).toReturn(12L);
        stub(transaction.getArtifactId()).toReturn(2L);
        stub(transaction.getOpenTransaction()).toReturn(sqlDate);
        stub(transaction.getCloseTransaction()).toReturn(sqlDate);
        assertTrue(transactionDao.insert(transaction));
    }

    @Test
    public void testIsTransactionUpdating(){
        Transaction transaction = mock(Transaction.class);
        stub(transaction.getId()).toReturn(999L);
        stub(transaction.getUserId()).toReturn(12L);
        stub(transaction.getArtifactId()).toReturn(8L);
        stub(transaction.getOpenTransaction()).toReturn(sqlDate);
        stub(transaction.getCloseTransaction()).toReturn(sqlDate);
        assertTrue(transactionDao.update(transaction));
    }

    @Test
    public void testIsTransactionDeleting(){
        assertTrue(transactionDao.delete(999L));
    }
}