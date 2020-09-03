package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Transaction;
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
class TransactionDaoTest {
    private Date sqlDate;
    private TransactionDao transactionDao;
    private Transaction transactionMock;

    @BeforeAll
    public void setUp(){
        sqlDate = new Date(2020, 10, 10);
        transactionDao = new TransactionDao();
        transactionMock = mock(Transaction.class);
        stub(transactionMock.getId()).toReturn(999L);
        stub(transactionMock.getUserId()).toReturn(12L);
        stub(transactionMock.getArtifactId()).toReturn(2L);
        stub(transactionMock.getOpenTransaction()).toReturn(sqlDate);
        stub(transactionMock.getCloseTransaction()).toReturn(sqlDate);
    }

    @Test
    public void testIsTransactionPresentFromResultSet() throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        stub(resultSetMock.getLong("id")).toReturn(999L);
        stub(resultSetMock.getLong("user_id")).toReturn(12L);
        stub(resultSetMock.getLong("artifact_id")).toReturn(2L);
        stub(resultSetMock.getDate("open_transaction")).toReturn(sqlDate);
        stub(resultSetMock.getDate("close_transaction")).toReturn(sqlDate);

        Transaction transaction = transactionDao.create(resultSetMock);

        assertAll("Check if db consistent with object",
                () -> assertEquals(transactionMock.getId(), transaction.getId()),
                () -> assertEquals(transactionMock.getUserId(), transaction.getUserId()),
                () -> assertEquals(transactionMock.getArtifactId(), transaction.getArtifactId()),
                () -> assertEquals(transactionMock.getOpenTransaction(), transaction.getOpenTransaction()),
                () -> assertEquals(transactionMock.getCloseTransaction(), transaction.getCloseTransaction())
        );
    }

    @Test
    public void testAreAllElementsPresent() throws SQLException {
        assertNotEquals(0, transactionDao.getAll().size());
    }

    @Test
    public void testIsTransactionInserting() throws SQLException {
        transactionDao.insert(transactionMock);
        Transaction transaction = transactionDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(transactionMock.getId(), transaction.getId()),
                () -> assertEquals(transactionMock.getUserId(), transaction.getUserId()),
                () -> assertEquals(transactionMock.getArtifactId(), transaction.getArtifactId()),
                () -> assertEquals(transactionMock.getOpenTransaction(), transaction.getOpenTransaction()),
                () -> assertEquals(transactionMock.getCloseTransaction(), transaction.getCloseTransaction())
        );
        transactionDao.delete(999L);
    }

    @Test
    public void testIsElementPresentById() throws SQLException {
        transactionDao.insert(transactionMock);
        assertEquals(999L, transactionDao.getById(999L).getId());
        transactionDao.delete(999L);
    }


    @Test
    public void testIsTransactionUpdating() throws SQLException {
        Transaction transactionMockUpdated = mock(Transaction.class);
        stub(transactionMockUpdated.getId()).toReturn(999L);
        stub(transactionMockUpdated.getUserId()).toReturn(12L);
        stub(transactionMockUpdated.getArtifactId()).toReturn(8L);
        stub(transactionMockUpdated.getOpenTransaction()).toReturn(sqlDate);
        stub(transactionMockUpdated.getCloseTransaction()).toReturn(sqlDate);

        transactionDao.insert(transactionMock);
        transactionDao.update(transactionMockUpdated);
        Transaction transaction = transactionDao.getById(999L);
        assertAll("Check if db consistent with object",
                () -> assertEquals(transactionMockUpdated.getId(), transaction.getId()),
                () -> assertEquals(transactionMockUpdated.getUserId(), transaction.getUserId()),
                () -> assertEquals(transactionMockUpdated.getArtifactId(), transaction.getArtifactId()),
                () -> assertEquals(transactionMockUpdated.getOpenTransaction(), transaction.getOpenTransaction()),
                () -> assertEquals(transactionMockUpdated.getCloseTransaction(), transaction.getCloseTransaction())
        );
        transactionDao.delete(999L);
    }

    @Test
    public void testIsTransactionDeleting(){
        transactionDao.insert(transactionMock);
        transactionDao.delete(999L);
        assertThrows(ElementNotFoundException.class, () -> transactionDao.getById(999L));
    }
}