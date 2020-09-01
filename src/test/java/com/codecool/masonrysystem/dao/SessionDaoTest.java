package com.codecool.masonrysystem.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SessionDaoTest {
    static SessionDao sessionDao;

    @BeforeEach
    void setUp() {
        sessionDao = new SessionDao();
    }

    @AfterEach
    void tearDown() {
        sessionDao = null;
    }

//    @Test
//    void getConnection() {
//
//    }

    @Test
    void getAllElements() {
    }

    @Test
    void getElementById() {
    }

    @Test
    void getHighestIdElement() {
    }

    @Test
    void deleteElement() {
    }

    @Test
    void create() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void testGetById() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
        // assertFalse(sessionDao.update( get session to ));
    }

    @Test
    void delete() {

        //        try {
//            assertFalse(sessionDao.delete("kra"));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }
}