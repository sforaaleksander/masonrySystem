package com.codecool.masonrySystem.Helpers;

import com.codecool.masonrySystem.DAO.UserDao;
import com.codecool.masonrySystem.Exception.InvalidLoginDataException;
import com.codecool.masonrySystem.Models.User;

import java.util.Map;

public class LoginHelper {
    private UserDao userDao;

    public LoginHelper(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean areCredentialsValid(Map<String, String> inputs) throws InvalidLoginDataException, ClassNotFoundException {
        String email = inputs.get("email");
        String password = inputs.get("password");
        User user = userDao.getUserByEmail(email);
        return user.getPassword().equals(password);
    }
}
