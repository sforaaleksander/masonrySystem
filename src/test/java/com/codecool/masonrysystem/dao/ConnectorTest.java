package com.codecool.masonrysystem.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorTest {
    private Connector connector = new Connector();

    @Test
    public void testIsConnectionPresent(){
        assertNotNull(connector.Connect());
    }
}