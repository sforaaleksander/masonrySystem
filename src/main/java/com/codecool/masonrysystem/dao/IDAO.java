package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.exception.ElementNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IDAO<T> {

    T getById(Long id) throws ElementNotFoundException, SQLException, ClassNotFoundException;

    boolean insert(T t) throws ElementNotFoundException, ClassNotFoundException, SQLException;

    boolean update(T t) throws ClassNotFoundException, SQLException;

    boolean delete(Long id) throws ClassNotFoundException;

    List<T> getAll() throws ElementNotFoundException, SQLException, ClassNotFoundException;
}