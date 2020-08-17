package com.codecool.masonrySystem.Handlers;

import com.codecool.masonrySystem.DAO.UserDao;
import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LoginHandler implements HttpHandler {
    private CookieHelper cookieHelper;
    private UserDao userDao;

    public LoginHandler(CookieHelper cookieHelper, UserDao userDao) {
        this.cookieHelper = cookieHelper;
        this.userDao = userDao;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("POST")) {
            postForm(httpExchange);
        } else if (method.equals("GET")) {
            getForm(httpExchange);
        }
    }

    private void getForm(HttpExchange httpExchange) {
    }

    private void postForm(HttpExchange httpExchange) {
    }
}
