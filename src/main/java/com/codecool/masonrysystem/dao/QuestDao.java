package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Quest;
import com.codecool.masonrysystem.model.Rank;

import java.sql.*;
import java.util.List;

public class QuestDao extends PostgresDAO<Quest> {

    public QuestDao() {
        super("quests");
    }

    @Override
    protected Quest create(ResultSet resultSet) throws SQLException {
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

    public List<Quest> getAll() throws ElementNotFoundException {
        return getAllElements();
    }

    @Override
    public Quest getById(Long id) throws ElementNotFoundException {
        return getElementById(id);
    }

    @Override
    public boolean insert(Quest quest) throws ElementNotFoundException {
        try {
            Connection connection = this.getConnection();
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
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Quest quest) {
        Long id = quest.getId();
        try {
            Connection connection = this.getConnection();
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
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return deleteElement(id);
    }
}
