package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.helper.LoginHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LogoutHandler implements HttpHandler {
    private final SessionDao sessionDao;
    private final LoginHelper loginHelper;

    public LogoutHandler(SessionDao sessionDao, LoginHelper loginHelper) {

        this.sessionDao = sessionDao;
        this.loginHelper = loginHelper;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String sessionId = exchange.getRequestHeaders().getFirst("Cookie")
                .replace("\"", "")
                .replace("sessionId=", "");
        System.out.println(sessionId);
        sessionDao.delete(sessionId);
        System.out.println("session successfully ended");
        removeCookie(exchange);
        loginHelper.redirectToLoginPage(exchange);
    }

    private void removeCookie(HttpExchange exchange) {
        String cookie = exchange.getRequestHeaders().getFirst("Cookie") + ";Max-age=0";
        exchange.getResponseHeaders().set("Set-Cookie", cookie);
    }
}

