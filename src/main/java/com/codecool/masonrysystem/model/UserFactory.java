package com.codecool.masonrysystem.model;

import com.codecool.masonrysystem.dao.LodgeDao;
import com.codecool.masonrysystem.exception.ElementNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {
    private LodgeDao lodgeDao = new LodgeDao();


    public User makeUser(ResultSet resultSet) throws SQLException {
        int userRole = resultSet.getInt("role_id");

        switch (userRole) {
            case 1:
                MasterMason masterMason = new MasterMason();
                masterMason.setId(resultSet.getLong("id"));
                masterMason.setFirstName(resultSet.getString("first_name"));
                masterMason.setLastName(resultSet.getString("last_name"));
                masterMason.setEmail(resultSet.getString("email"));
                masterMason.setPassword(resultSet.getString("password"));
                masterMason.setIsActive(resultSet.getBoolean("is_active"));
                return masterMason;
            case 2:
                Journeyman journeyman = new Journeyman();
                journeyman.setId(resultSet.getLong("id"));
                journeyman.setFirstName(resultSet.getString("first_name"));
                journeyman.setLastName(resultSet.getString("last_name"));
                journeyman.setEmail(resultSet.getString("email"));
                journeyman.setPassword(resultSet.getString("password"));
                journeyman.setIsActive(resultSet.getBoolean("is_active"));
                return journeyman;
            default:
                Apprentice apprentice = new Apprentice();
                apprentice.setId(resultSet.getLong("id"));
                apprentice.setFirstName(resultSet.getString("first_name"));
                apprentice.setLastName(resultSet.getString("last_name"));
                apprentice.setEmail(resultSet.getString("email"));
                apprentice.setPassword(resultSet.getString("password"));
                apprentice.setSpiritPoints(resultSet.getInt("spirit_points"));
//                try {
//                    Lodge lodge = lodgeDao.getById(resultSet.getLong("lodge_id"));
//                    apprentice.setLodge(lodge);
//                } catch (ElementNotFoundException e) {
//                    e.printStackTrace();
//                }
                apprentice.setRank(Rank.values()[resultSet.getInt("rank_id")]);
                apprentice.setIsActive(resultSet.getBoolean("is_active"));
                return apprentice;
        }
    }
}
