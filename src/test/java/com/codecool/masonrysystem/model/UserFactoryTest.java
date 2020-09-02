package com.codecool.masonrysystem.model;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

class UserFactoryTest {

    @Test
    void makeUserTest() {
        UserFactory userFactory = new UserFactory();
        ResultSet resultSet = mock(ResultSet.class);
        Map<Integer, User> userTypes = new HashMap<>();
        userTypes.put(1, new MasterMason());
        userTypes.put(2, new Journeyman());
        userTypes.put(3, new Apprentice());
        Set<Map.Entry<Integer, User>> userTypeSet = userTypes.entrySet();
        for(Map.Entry<Integer, User> userType: userTypeSet){
            try {
                stub(resultSet.getInt("role_id")).toReturn(userType.getKey());
                stub(resultSet.getLong("id")).toReturn(7L);
                stub(resultSet.getString("first_name")).toReturn("Staszek");
                stub(resultSet.getString("last_name")).toReturn("Ptaszek");
                stub(resultSet.getString("email")).toReturn("elo@elo.320");
                stub(resultSet.getString("password")).toReturn("abracadabra");
                stub(resultSet.getBoolean("is_active")).toReturn(true);
                assertEquals(userType.getValue().getClass(), userFactory.makeUser(resultSet).getClass());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}