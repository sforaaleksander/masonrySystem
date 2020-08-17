package com.codecool.masonrySystem.DAO;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements IDAO<Transaction> {

    private Transaction create(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getLong("id"));
        transaction.setUserId(resultSet.getLong("user_id"));
        transaction.setArtifactId(resultSet.getLong("artifact_id"));
        transaction.setOpenTransaction(resultSet.getDate("open_transaction"));
        transaction.setCloseTransaction(resultSet.getDate("close_transaction"));
        return transaction;
    }


    public List<Transaction> getAll() throws ElementNotFoundException, ClassNotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM transactions;");
            while (rs.next()) {
                transactions.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Transactions not found");
    }

    @Override
    public Transaction getById(Long id) throws ClassNotFoundException, ElementNotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM transactions WHERE id = ?");
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.setLong(1, id);
            while (rs.next()) {
                transactions.add(create(rs));
            }
            rs.close();
            preparedStatement.close();
            connection.close();
            return transactions.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Transactions not found");
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
            preparedStatement.executeQuery();
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
            preparedStatement.executeQuery();
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
        Connection connection = this.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM transactions WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
