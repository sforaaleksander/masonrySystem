package com.codecool.masonrysystem.dao;

import com.codecool.masonrysystem.helper.IdGenerator;
import com.codecool.masonrysystem.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

class SessionDaoTest {
    static SessionDao sessionDao;
    static Journeyman journeyman;
    static MasterMason masterMason;
    static Apprentice apprentice;

    @BeforeAll
    public static void init() {
        sessionDao = new SessionDao();
        apprentice = mock(Apprentice.class);
        journeyman = mock(Journeyman.class);
        masterMason = mock(MasterMason.class);
    }

    @Test
    void insert() {
        String sessionId = (new IdGenerator()).generateId(16);
        Session session = mock(Session.class);
        stub(session.getSessionId()).toReturn(sessionId);
        stub(session.getUserId()).toReturn((long)13);
        try {
            assertTrue(sessionDao.insert(session));
            assertEquals(13, sessionDao.getById(sessionId).getUserId());
            sessionDao.delete(sessionId);
        } catch (SQLException throwables) {
            assertTrue(false);
            throwables.printStackTrace();
        }
    }

    @Test
    void getAllElements() {
        try {
            HashSet<Session> sessions = new HashSet<Session>();
            sessionDao.getAllElements().stream().forEach(s -> {
                assertEquals("class com.codecool.masonrysystem.model.Session", s.getClass().toString());
                assertNotNull(s.getUserId());
                assertNotNull(s.getSessionId());
                assertFalse(sessions.contains(s));
                sessions.add(s);
            });
        } catch (SQLException throwables) {
            assertTrue(false);
            throwables.printStackTrace();
        }
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