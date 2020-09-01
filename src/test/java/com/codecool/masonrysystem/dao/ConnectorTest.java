package com.codecool.masonrysystem.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorTest {

    @Test
    void connect() {
        assertNotNull((new Connector()).Connect());
    }
}