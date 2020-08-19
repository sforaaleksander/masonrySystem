package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Journeyman;
import com.codecool.masonrysystem.model.Lodge;

import java.sql.*;
import java.util.List;

public class LodgeDao extends PostgresDAO<Lodge> implements IDAO<Lodge> {

    public LodgeDao() {
        super("lodges");
    }

    @Override
    protected Lodge create(ResultSet resultSet) throws SQLException {
        Journeyman journeyman = null;
        Lodge lodge = new Lodge();
        lodge.setId(resultSet.getLong("id"));
        lodge.setName(resultSet.getString("name"));
        try {
            journeyman = (Journeyman) new UserDao().getById(resultSet.getLong("owner_id"));
        } catch (ClassNotFoundException | ElementNotFoundException e) {
            e.printStackTrace();
        }
        lodge.setOwner(journeyman);
        return lodge;
    }

    public List<Lodge> getAll() throws ElementNotFoundException, ClassNotFoundException {
        return getAllElements();
    }

    @Override
    public Lodge getById(Long id) throws ClassNotFoundException, ElementNotFoundException {
        return getElementById(id);
    }

    @Override
    public boolean insert(Lodge lodge) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO lodges" +
                    "(id, name, owner_id) VALUES " +
                    "(?, ?, ?)");
            preparedStatement.setLong(1, lodge.getId());
            preparedStatement.setString(2, lodge.getName());
            preparedStatement.setLong(3, lodge.getOwner().getId());
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
    public boolean update(Lodge lodge) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        Long id = lodge.getId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE lodges SET " +
                    "name=?, owner_id=? WHERE id = ?");
            preparedStatement.setString(1, lodge.getName());
            preparedStatement.setLong(2, lodge.getOwner().getId());
            preparedStatement.setLong(3, id);
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
