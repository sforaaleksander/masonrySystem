package com.codecool.masonrySystem.DAO;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Models.Apprentice;
import com.codecool.masonrySystem.Models.User;
import com.codecool.masonrySystem.Models.Rank;
import com.codecool.masonrySystem.Models.UserFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IDAO<User> {
    private final UserFactory userFactory;

    public UserDao() {
        this.userFactory = new UserFactory();
    }

    private User create(ResultSet resultSet) throws SQLException {
        return userFactory.makeUser(resultSet);
    }


    public List<User> getAll() throws ElementNotFoundException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        Connector connector = new Connector();
        Connection connection = connector.Connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users;");
            while (rs.next()) {
                users.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Users not found");
    }

    @Override
    public User getById(Long id) throws ClassNotFoundException, ElementNotFoundException {
        List<User> users = new ArrayList<>();
        Connector connector = new Connector();
        Connection connection = connector.Connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.setLong(1, id);
            while (rs.next()) {
                users.add(create(rs));
            }
            rs.close();
            preparedStatement.close();
            connection.close();
            return users.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Users not found");
    }

    @Override
    public boolean insert(User user) throws ClassNotFoundException, ElementNotFoundException {
        Integer spiritPoints = null;
        Integer lodgeId = null;
        Connector connector = new Connector();
        Connection connection = connector.Connect();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users" +
                    "(first_name, last_name, email, password, spirit_points, lodge_id, role_id, rank_id, is_active) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            if (user.getRank() !=Rank.AINSOPHAUR || user.getRank() !=Rank.THEILLUMINATI) {
                Apprentice apprentice = (Apprentice) user;
                spiritPoints = apprentice.getSpiritPoints();
                lodgeId = apprentice.getLodgeId();
            }
            preparedStatement.setInt(6, spiritPoints);
            preparedStatement.setInt(7, lodgeId);
            preparedStatement.setInt(8, user.getRank().ordinal()); //TODO check if correct
            preparedStatement.setBoolean(9, user.getIsActive());
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
    public boolean update(User user) throws ClassNotFoundException {
        Integer spiritPoints = null;
        Integer lodgeId = null;
        Connector connector = new Connector();
        Connection connection = connector.Connect();
        Long id = user.getId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET " +
                    "first_name=?, last_name=?, email=?, password=?, spirit_points=?, lodge_id=?, role_id=?, rank_id=?, is_active=? WHERE id = ?");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            if (user.getRank() !=Rank.AINSOPHAUR || user.getRank() !=Rank.THEILLUMINATI) {
                Apprentice apprentice = (Apprentice) user;
                spiritPoints = apprentice.getSpiritPoints();
                lodgeId = apprentice.getLodgeId();
            }
            preparedStatement.setInt(5, spiritPoints);
            preparedStatement.setInt(6, lodgeId);
            preparedStatement.setInt(7, user.getRank().ordinal()); //TODO check if correct
            preparedStatement.setBoolean(8, user.getIsActive());
            preparedStatement.setLong(9, id);
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
        Connector connector = new Connector();
        Connection connection = connector.Connect();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
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
