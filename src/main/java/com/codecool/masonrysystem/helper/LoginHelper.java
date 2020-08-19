package com.codecool.masonrysystem.helper;

import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.model.User;

import java.util.Map;

public class LoginHelper {
    private UserDao userDao;
    private IdGenerator idGenerator;

    public LoginHelper(UserDao userDao) {
        this.userDao = userDao;
        this.idGenerator = new IdGenerator();
    }

    public boolean areCredentialsValid(Map<String, String> inputs) throws ClassNotFoundException {
        String email = inputs.get("email");
        String password = inputs.get("password");
        User user = userDao.getUserByEmail(email);
        return user.getPassword().equals(password);
    }

    public IdGenerator getIdGenerator() {
        return idGenerator;
    }
}
