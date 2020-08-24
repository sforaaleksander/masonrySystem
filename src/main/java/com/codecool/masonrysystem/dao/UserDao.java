package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Apprentice;
import com.codecool.masonrysystem.model.User;
import com.codecool.masonrysystem.model.Rank;
import com.codecool.masonrysystem.model.UserFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends PostgresDAO<User> implements IDAO<User> {
    private final UserFactory userFactory;

    public UserDao() {
        super("users");
        this.userFactory = new UserFactory();
    }

    @Override
    protected User create(ResultSet resultSet) throws SQLException {
        return userFactory.makeUser(resultSet);
    }
    
    public List<User> getAll() throws ElementNotFoundException {
        return getAllElements();
    }

    @Override
    public User getById(Long id) throws ElementNotFoundException {
        return getElementById(id);
    }

    @Override
    public boolean insert(User user) throws ClassNotFoundException, SQLException {
        Integer spiritPoints = null;
        Long lodgeId = null;
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users" +
                    "(first_name, last_name, email, password, spirit_points, lodge_id, role_id, rank_id, is_active) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            if (user.getRank() != Rank.AINSOPHAUR || user.getRank() != Rank.THEILLUMINATI) {
                Apprentice apprentice = (Apprentice) user;
                spiritPoints = apprentice.getSpiritPoints();
                lodgeId = apprentice.getLodge().getId();
            }
            preparedStatement.setInt(6, spiritPoints);
            preparedStatement.setLong(7, lodgeId);
            preparedStatement.setInt(8, user.getRank().ordinal()); //TODO check if correct
            preparedStatement.setBoolean(9, user.getIsActive());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(User user) throws ClassNotFoundException, SQLException {
        Integer spiritPoints = null;
        Long lodgeId = null;
        Long id = user.getId();
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET " +
                    "first_name=?, last_name=?, email=?, password=?, spirit_points=?, lodge_id=?, role_id=?, rank_id=?, is_active=? WHERE id = ?");
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            if (user.getRank() != Rank.AINSOPHAUR || user.getRank() != Rank.THEILLUMINATI) {
                Apprentice apprentice = (Apprentice) user;
                spiritPoints = apprentice.getSpiritPoints();
                lodgeId = apprentice.getLodge().getId();
            }
            preparedStatement.setInt(5, spiritPoints);
            preparedStatement.setLong(6, lodgeId);
            preparedStatement.setInt(7, user.getRank().ordinal()); //TODO check if correct
            preparedStatement.setBoolean(8, user.getIsActive());
            preparedStatement.setLong(9, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return deleteElement(id);
    }

    public User getUserByEmail(String email) throws ElementNotFoundException, ClassNotFoundException, SQLException {
        User user;
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email=?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = create(rs);
                rs.close();
                preparedStatement.close();
                connection.close();
                return user;
            }
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
        }
        throw new ElementNotFoundException("User with given email could not be found");
    }

    public List<User> getUsersByRole(int roleId) throws ElementNotFoundException, ClassNotFoundException, SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE role_id=?");
            preparedStatement.setInt(1, roleId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                users.add(create(rs));
            }
            rs.close();
            preparedStatement.close();
            connection.close();
            return users;
        } catch (SQLException e) {
            connection.close();
            e.printStackTrace();
        }
        throw new ElementNotFoundException("User with given rank could not be found");
    }
}
