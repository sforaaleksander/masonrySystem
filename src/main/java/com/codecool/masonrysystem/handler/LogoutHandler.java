package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.SessionDao;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class LogoutHandler implements HttpHandler {
    private final SessionDao sessionDao;

    public LogoutHandler(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String sessionId = exchange.getRequestHeaders().getFirst("Cookie")
                .replace("\"", "")
                .replace("sessionId=", "");
        System.out.println(sessionId);
        try {
            sessionDao.delete(sessionId);
            System.out.println("session successfully ended");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        removeCookie(exchange);
        redirectToLoginPage(exchange);
    }

    private void redirectToLoginPage(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Location", "login");
        exchange.sendResponseHeaders(302, -1);
        exchange.close();
    }

    private void removeCookie(HttpExchange exchange) {
        String cookie = exchange.getRequestHeaders().getFirst("Cookie") + ";Max-age=0";
        exchange.getResponseHeaders().set("Set-Cookie", cookie);
    }
}

