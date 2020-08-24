package com.codecool.masonrysystem.helper;

import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.model.User;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class LoginHelper {
    private UserDao userDao;
    private IdGenerator idGenerator;

    public LoginHelper(UserDao userDao) {
        this.userDao = userDao;
        this.idGenerator = new IdGenerator();
    }

    public boolean areCredentialsValid(Map<String, String> inputs) throws ElementNotFoundException, SQLException, ClassNotFoundException {
        String email = inputs.get("email");
        String password = inputs.get("password");
        User user = userDao.getUserByEmail(email);
        return user.getPassword().equals(password);
    }

    public void redirectToLoginPage(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Location", "login");
        exchange.sendResponseHeaders(302, -1);
        exchange.close();
    }

    public IdGenerator getIdGenerator() {
        return idGenerator;
    }
}
