package com.codecool.masonrySystem.DAO;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Models.Quest;
import com.codecool.masonrySystem.Models.Rank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDao implements IDAO<Quest> {

    private Quest create(ResultSet resultSet) throws SQLException {
        Quest quest = new Quest();
        quest.setId(resultSet.getLong("id"));
        quest.setName(resultSet.getString("name"));
        quest.setReward(resultSet.getInt("reward"));
        quest.setRequiredRank(Rank.values()[resultSet.getInt("required_rank") + 1]);         //TODO check this
        quest.setDescription(resultSet.getString("description"));
        quest.setIsActive(resultSet.getBoolean("is_active"));
        quest.setExpirationDate(resultSet.getDate("expiration_date"));
        return quest;
    }


    public List<Quest> getAll() throws ElementNotFoundException, ClassNotFoundException {
        List<Quest> quests = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM quests;");
            while (rs.next()) {
                quests.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return quests;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Quests not found");
    }

    @Override
    public Quest getById(Long id) throws ClassNotFoundException, ElementNotFoundException {
        List<Quest> quests = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quests WHERE id = ?");
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.setLong(1, id);
            while (rs.next()) {
                quests.add(create(rs));
            }
            rs.close();
            preparedStatement.close();
            connection.close();
            return quests.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Quests not found");
    }

    @Override
    public boolean insert(Quest quest) throws ClassNotFoundException, ElementNotFoundException {
        Connection connection = this.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO quests" +
                    "(id, name, reward, required_rank, description, is_active, expiration_date, is_collective) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, quest.getId());
            preparedStatement.setString(2, quest.getName());
            preparedStatement.setInt(3, quest.getReward());
            preparedStatement.setInt(4, quest.getRequiredRank().ordinal()); //TODO check if correct
            preparedStatement.setString(5, quest.getDescription());
            preparedStatement.setBoolean(6, quest.getIsActive());
            preparedStatement.setDate(7, (Date) quest.getExpirationDate());
            preparedStatement.setBoolean(8, quest.getIsCollective());
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
    public boolean update(Quest quest) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        Long id = quest.getId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE quests SET " +
                    "name=?, reward=?, required_rank=?, description=?, is_active=?, expiration_date=?, is_collective=? WHERE id = ?");
            preparedStatement.setString(1, quest.getName());
            preparedStatement.setInt(2, quest.getReward());
            preparedStatement.setInt(3, quest.getRequiredRank().ordinal()); //TODO and this
            preparedStatement.setString(4, quest.getDescription());
            preparedStatement.setBoolean(5, quest.getIsActive());
            preparedStatement.setDate(6, (Date) quest.getExpirationDate());
            preparedStatement.setBoolean(7, quest.getIsCollective());
            preparedStatement.setLong(8, id);
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM quests WHERE id = ?");
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
