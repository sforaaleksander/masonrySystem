package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class PostgresDAO<T> implements IDAO<T> {
    public final String TABLENAME;

    public PostgresDAO(String tableName) {

        TABLENAME = tableName;
    }

    public List<T> getAllElements() throws ElementNotFoundException {
        List<T> elements = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + this.TABLENAME + ";");
            while (rs.next()) {
                elements.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return elements;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException(TABLENAME + " could not be found");
    }

    public T getElementById(Long id) throws ElementNotFoundException {
        T element;
        try {
            Connection connection = this.getConnection();
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException(this.TABLENAME + " not found");
    }

    public T getHighestIdElement() throws ElementNotFoundException {
        T element;
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * " +
                    "FROM " + this.TABLENAME+ " order by id desc limit 1");
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                element = create(rs);
                rs.close();
                preparedStatement.close();
                connection.close();
                return element;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException(this.TABLENAME + " not found");
    }

    public boolean deleteElement(Long id)  {
        try {
            Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + this.TABLENAME + " WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    abstract T create(ResultSet rs) throws SQLException;
}
