package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Transaction;

import java.sql.*;
import java.util.List;

public class TransactionDao extends PostgresDAO<Transaction> implements IDAO<Transaction> {

    public TransactionDao() {
        super("transactions");
    }

    @Override
    protected Transaction create(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getLong("id"));
        transaction.setUserId(resultSet.getLong("user_id"));
        transaction.setArtifactId(resultSet.getLong("artifact_id"));
        transaction.setOpenTransaction(resultSet.getDate("open_transaction"));
        transaction.setCloseTransaction(resultSet.getDate("close_transaction"));
        return transaction;
    }

    public List<Transaction> getAll() throws ElementNotFoundException, ClassNotFoundException {
        return getAllElements();
    }

    @Override
    public Transaction getById(Long id) throws ClassNotFoundException, ElementNotFoundException {
        return getElementById(id);
    }

    @Override
    public boolean insert(Transaction transaction) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transactions " +
                            "(id, user_id, artifact_id, open_transaction, close_transaction) VALUES " +
                            "(?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, transaction.getId());
            preparedStatement.setLong(2, transaction.getUserId());
            preparedStatement.setLong(3, transaction.getArtifactId());
            preparedStatement.setDate(4, (Date) transaction.getOpenTransaction());
            preparedStatement.setDate(5, (Date) transaction.getCloseTransaction());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Transaction transaction) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        Long id = transaction.getId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE transactions SET " +
                    "user_id=?, artifact_id=?, open_transaction=?, close_transaction=? WHERE id = ?");
            preparedStatement.setLong(1, transaction.getUserId());
            preparedStatement.setLong(2, transaction.getArtifactId());
            preparedStatement.setDate(3, (Date) transaction.getOpenTransaction());
            preparedStatement.setDate(4, (Date) transaction.getCloseTransaction());
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) throws ClassNotFoundException {
        return deleteElement(id);
    }
}
