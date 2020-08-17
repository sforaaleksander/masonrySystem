package com.codecool.masonrySystem.DAO;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Models.CollectiveTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectiveTransactionDao implements IDAO<CollectiveTransaction> {

    private CollectiveTransaction create(ResultSet resultSet) throws SQLException {
        CollectiveTransaction collectiveTransaction = new CollectiveTransaction();
        collectiveTransaction.setId(resultSet.getLong("id"));
        collectiveTransaction.setTransactionId(resultSet.getLong("transaction_id"));
        collectiveTransaction.setUserId(resultSet.getLong("user_id"));
        collectiveTransaction.setDonationDate(resultSet.getDate("donation_date"));
        collectiveTransaction.setAmount(resultSet.getInt("amount"));
        return collectiveTransaction;
    }


    public List<CollectiveTransaction> getAll() throws ElementNotFoundException, ClassNotFoundException {
        List<CollectiveTransaction> collectiveTransactions = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM collective_transactions;");
            while (rs.next()) {
                collectiveTransactions.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return collectiveTransactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Collective transactions not found");
    }

    @Override
    public CollectiveTransaction getById(Long id) throws ClassNotFoundException, ElementNotFoundException {
        CollectiveTransaction collectiveTransaction;
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM collective_transactions WHERE id = ?");
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.setLong(1, id);
            if (rs.next()) {
                collectiveTransaction = create(rs);
                rs.close();
                preparedStatement.close();
                connection.close();
                return collectiveTransaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("CollectiveTransactions not found");
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM collective_transactions WHERE id = ?");
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
