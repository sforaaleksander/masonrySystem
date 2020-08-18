package com.codecool.masonrySystem.DAO;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Models.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionDao implements IDAO<Session> {

    private Session create(ResultSet resultSet) throws SQLException {
        Session session = new Session();
        session.setSessionId(resultSet.getString("session_id"));
        session.setUserId(resultSet.getLong("user_id"));
        return session;
    }


    public List<Session> getAll() throws ElementNotFoundException, ClassNotFoundException {
        List<Session> sessions = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM sessions;");
            while (rs.next()) {
                sessions.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return sessions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Sessions not found");
    }

    @Override
    public Session getById(Long id) {
        return null;
    }

    public Session getById(String id) throws ClassNotFoundException, ElementNotFoundException {
        Session session;
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sessions WHERE session_id = ?");
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.setString(1, id);
            if (rs.next()) {
                session = create(rs);
                rs.close();
                preparedStatement.close();
                connection.close();
                return session;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("Session not found");
    }

    @Override
    public boolean insert(Session session) throws ClassNotFoundException {
        Connection connection = this.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sessions" +
                    "(session_id, user_id) VALUES " +
                    "(?, ?)");
            preparedStatement.setString(1, session.getSessionId());
            preparedStatement.setLong(2, session.getUserId());
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
    public boolean update(Session session) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public boolean delete(String id) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM sessions WHERE session_id = ?");
            preparedStatement.setString(1, id);
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

