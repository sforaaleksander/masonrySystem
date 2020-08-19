package com.codecool.masonrySystem.DAO;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class PostgresDAO<T> implements IDAO<T> {
    protected final String TABLENAME;

    protected PostgresDAO(String tableName) {
        TABLENAME = tableName;
    }

    protected List<T> getAllElements() throws ElementNotFoundException, ClassNotFoundException {
        List<T> elements = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + this.TABLENAME +";");
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

    abstract T create(ResultSet rs) throws SQLException;}
