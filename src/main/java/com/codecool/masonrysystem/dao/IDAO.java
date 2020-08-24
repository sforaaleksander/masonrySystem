package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IDAO<T> {

    T getById(Long id) throws ElementNotFoundException;

    boolean insert(T t) throws ElementNotFoundException, ClassNotFoundException, SQLException;

    boolean update(T t) throws ClassNotFoundException, SQLException;

    boolean delete(Long id);

    List<T> getAll() throws ElementNotFoundException;

    default Connection getConnection(String... methodName) throws ClassNotFoundException {
        Connector connector = new Connector();
        if (methodName.length > 0) {
            System.out.println(methodName[0]);
        } else {
            System.out.println("method did not pass it's name");
        }
        return connector.Connect();
    }
}