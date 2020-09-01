package com.codecool.masonrysystem.helper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {
    static IdGenerator idGenerator;

    @BeforeEach
    void setUp() {
        idGenerator = new IdGenerator();
    }

    @AfterEach
    void tearDown() {
        idGenerator = null;
    }

    @Test
    void generateId() {
        assertNotEquals(idGenerator.generateId(26), new IdGenerator().generateId(26));
        assertNotEquals(idGenerator.generateId(26), idGenerator.generateId(26));
        assertEquals(52, idGenerator.generateId(26).length());
    }

    @Test
    void getAlgorithm() {
        assertEquals("MD5", idGenerator.getAlgorithm());
    }

    @Test
    void setAlgorithm() {
        idGenerator.setAlgorithm("new algoirthm");
        assertEquals("new algoirthm", idGenerator.getAlgorithm());
    }
}