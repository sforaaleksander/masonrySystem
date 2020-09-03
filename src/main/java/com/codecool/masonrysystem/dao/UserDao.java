package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends PostgresDAO<User> implements IDAO<User> {
    private final UserFactory userFactory;

    public UserDao() {
        super("users");
        this.userFactory = new UserFactory();
    }

    public UserDao(LodgeDao lodgeDao) {
        super("users");
        this.userFactory = new UserFactory(lodgeDao);
    }

    @Override
    protected User create(ResultSet resultSet) throws SQLException {
        return userFactory.makeUser(resultSet);
    }
    
    public List<User> getAll() throws ElementNotFoundException, SQLException {
        return getAllElements();
    }

    @Override
    public User getById(Long id) throws ElementNotFoundException, SQLException {
        return getElementById(id);
    }

    @Override
    public boolean insert(User user) {
        try {
            return executeInsertStatement(user, null, null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean insert(Apprentice user) {
        Integer spiritPoints = user.getSpiritPoints();
        Long lodgeId = user.getLodge().getId();
        try {
            return executeInsertStatement(user, spiritPoints, lodgeId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean executeInsertStatement(User user, Integer spiritPoints, Long lodgeId) throws SQLException {
        String statement = "INSERT INTO users" +
                "(first_name, last_name, email, password, spirit_points, lodge_id, role_id, rank_id, is_active, id) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return executeStatement(user, statement, spiritPoints, lodgeId);
    }

    @Override
    public boolean update(User user) throws SQLException {
        try {
            return executeUpdateStatement(user, null, null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean update(Apprentice user) throws SQLException {
        Integer spiritPoints = user.getSpiritPoints();
        Long lodgeId = user.getLodge().getId();
        try {
            return executeUpdateStatement(user, spiritPoints, lodgeId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean executeUpdateStatement(User user, Integer spiritPoints, Long lodgeId ) throws SQLException {
        String statement = "UPDATE users SET " +
                "first_name=?, last_name=?, email=?, password=?, spirit_points=?, lodge_id=?, role_id=?, rank_id=?, is_active=?" +
                " WHERE id = ?";
        return executeStatement(user, statement, spiritPoints, lodgeId);
    }

    @Override
    public boolean delete(Long id) {
        return deleteElement(id);
    }

    public User getUserByEmail(String email) throws ElementNotFoundException, SQLException {
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

    public List<User> getUsersByRole(int roleId) throws ElementNotFoundException, SQLException {
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

    public boolean executeStatement(User user, String statement, Integer spiritPoints, Long lodgeId) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement = prepareStatement(connection, user, statement);
        preparedStatement = updateSpiritAndLodge(preparedStatement, spiritPoints, lodgeId);
        System.out.println(preparedStatement.toString());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return true;
    }

    public PreparedStatement updateSpiritAndLodge(PreparedStatement preparedStatement, Integer spiritPoints, Long lodgeId) throws SQLException {
        if (spiritPoints != null){
            System.out.println("null points");
            preparedStatement.setInt(5, spiritPoints);
            preparedStatement.setLong(6, lodgeId);
        } else {
            System.out.println("points");
            preparedStatement.setNull(5, java.sql.Types.INTEGER);
            preparedStatement.setNull(6, java.sql.Types.INTEGER);
        }
        return preparedStatement;
    }

    public PreparedStatement prepareStatement(Connection connection, User user, String statement) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setLong(10, user.getId());
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        int roleId = 3;
        switch (user.getClass().toString()){
            case ("class com.codecool.masonrysystem.model.MasterMason"):
                roleId = 1;
                break;
            case ("class com.codecool.masonrysystem.model.Journeyman"):
                roleId = 2;
                break;
            default:
                roleId = 3;
        }
        preparedStatement.setInt(7, roleId);
        preparedStatement.setInt(8, user.getRank().ordinal()); //TODO check if correct
        preparedStatement.setBoolean(9, user.getIsActive());
        return preparedStatement;
    }
}
