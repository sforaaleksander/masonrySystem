package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;

import java.sql.Connection;
import java.util.List;

public interface IDAO<T> {

    T getById(Long id) throws ElementNotFoundException;

    boolean insert(T t) throws ElementNotFoundException;

    boolean update(T t);

    boolean delete(Long id);

    List<T> getAll() throws ElementNotFoundException;

    default Connection getConnection() throws ClassNotFoundException {
        Connector connector = new Connector();
        return connector.Connect();
    }
}