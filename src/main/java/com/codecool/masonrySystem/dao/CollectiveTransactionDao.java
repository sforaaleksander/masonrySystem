package com.codecool.masonrySystem.dao;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Models.CollectiveTransaction;

import java.sql.*;
import java.util.List;

public class CollectiveTransactionDao extends PostgresDAO<CollectiveTransaction> implements IDAO<CollectiveTransaction> {

    public CollectiveTransactionDao() {
        super("collective_transactions");
    }

    @Override
    protected CollectiveTransaction create(ResultSet resultSet) throws SQLException {
        CollectiveTransaction collectiveTransaction = new CollectiveTransaction();
        collectiveTransaction.setId(resultSet.getLong("id"));
        collectiveTransaction.setTransactionId(resultSet.getLong("transaction_id"));
        collectiveTransaction.setUserId(resultSet.getLong("user_id"));
        collectiveTransaction.setDonationDate(resultSet.getDate("donation_date"));
        collectiveTransaction.setAmount(resultSet.getInt("amount"));
        return collectiveTransaction;
    }

    public List<CollectiveTransaction> getAll() throws ElementNotFoundException, ClassNotFoundException {
        return getAllElements();
    }

    @Override
    public CollectiveTransaction getById(Long id) throws ClassNotFoundException, ElementNotFoundException {
        return getElementById(id);
    }

    @Override
    public boolean insert(CollectiveTransaction collectiveTransaction) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO collective_transactions" +
                    "(id, transaction_id, user_id, donation_date, amount) VALUES " +
                    "(?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, collectiveTransaction.getId());
            preparedStatement.setLong(2, collectiveTransaction.getTransactionId());
            preparedStatement.setLong(3, collectiveTransaction.getUserId());
            preparedStatement.setDate(4, (Date) collectiveTransaction.getDonationDate());
            preparedStatement.setInt(5, collectiveTransaction.getAmount());
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
    public boolean update(CollectiveTransaction collectiveTransaction) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        Long id = collectiveTransaction.getId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE collective_transactions SET " +
                    "transaction_id=?, user_id=?, donation_date=?, amount=? WHERE id = ?");
            preparedStatement.setLong(1, collectiveTransaction.getTransactionId());
            preparedStatement.setLong(2, collectiveTransaction.getUserId());
            preparedStatement.setDate(3, (Date) collectiveTransaction.getDonationDate());
            preparedStatement.setInt(4, collectiveTransaction.getAmount());
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
