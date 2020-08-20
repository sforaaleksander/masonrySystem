package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PostgresDAO<T> implements IDAO<T> {
    protected final String TABLENAME;

    public PostgresDAO(String tableName) {

        TABLENAME = tableName;
    }

    protected List<T> getAllElements() throws ElementNotFoundException, ClassNotFoundException {
        List<T> elements = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + this.TABLENAME + ";");
            while (rs.next()) {
                elements.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return elements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException(TABLENAME + " could not be found");
    }

    protected T getElementById(Long id) throws ElementNotFoundException, ClassNotFoundException {
        T element;
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + this.TABLENAME + " WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                element = create(rs);
                rs.close();
                preparedStatement.close();
                connection.close();
                return element;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException(this.TABLENAME + " not found");
    }

    protected boolean deleteElement(Long id) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + this.TABLENAME + " WHERE id = ?");
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

    abstract T create(ResultSet rs) throws SQLException;
}
