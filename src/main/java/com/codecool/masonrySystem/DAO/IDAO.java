package com.codecool.masonrySystem.DAO;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;

import java.util.List;

public interface IDAO<T> {

    T getById(Long id) throws ClassNotFoundException, ElementNotFoundException;
    boolean insert(T t) throws ClassNotFoundException, ElementNotFoundException;
    boolean update(T t) throws ClassNotFoundException;
    boolean delete(Long id) throws ClassNotFoundException;

    List<T> getAll() throws ElementNotFoundException, ClassNotFoundException;
}