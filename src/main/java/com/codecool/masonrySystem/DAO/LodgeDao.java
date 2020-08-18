package com.codecool.masonrySystem.DAO;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Models.Journeyman;
import com.codecool.masonrySystem.Models.Lodge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LodgeDao implements IDAO<Lodge> {

    private Lodge create(ResultSet resultSet) throws SQLException {
        Journeyman journeyman = null;
        Lodge lodge = new Lodge();
        lodge.setId(resultSet.getLong("id"));
        lodge.setName(resultSet.getString("name"));
        try {
            journeyman = (Journeyman) new UserDao().getById(resultSet.getLong("id"));
        } catch (ClassNotFoundException | ElementNotFoundException e) {
            e.printStackTrace();
        }
        lodge.setOwner(journeyman);
        return lodge;
    }

    public List<Lodge> getAll() throws ElementNotFoundException, ClassNotFoundException {
        List<Lodge> lodges = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM lodges;");
            while (rs.next()) {
                lodges.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return lodges;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Lodges not found");
    }

    @Override
    public Lodge getById(Long id) throws ClassNotFoundException, ElementNotFoundException {
        Lodge lodge;
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM lodges WHERE id = ?");
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.setLong(1, id);
            if (rs.next()) {
                lodge = create(rs);
                rs.close();
                preparedStatement.close();
                connection.close();
                return lodge;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Lodges not found");
    }

    @Override
    public boolean insert(Lodge lodge) throws ClassNotFoundException, ElementNotFoundException {
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
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM lodges WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
