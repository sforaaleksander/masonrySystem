package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.Session;

import java.sql.*;
import java.util.List;

public class SessionDao extends PostgresDAO<Session> implements IDAO<Session> {

    public SessionDao() {
        super("sessions");
    }

    @Override
    protected Session create(ResultSet resultSet) throws SQLException {
        Session session = new Session();
        session.setSessionId(resultSet.getString("session_id"));
        session.setUserId(resultSet.getLong("user_id"));
        return session;
    }

    public List<Session> getAll() throws ElementNotFoundException, SQLException {
        return getAllElements();
    }

    @Override
    public Session getById(Long id) {
        return null;
    }

    public Session getById(String id) throws ElementNotFoundException, SQLException {
        Session session;
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sessions WHERE session_id = ?");
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
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
        connection.close();
        throw new ElementNotFoundException("Session not found");
    }

    @Override
    public boolean insert(Session session) throws SQLException {
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
            connection.close();
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

    public boolean delete(String id) throws SQLException {
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
        connection.close();
        return false;
    }
}

