package com.codecool.masonrySystem.handler;

import com.codecool.masonrySystem.dao.SessionDao;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LogoutHandler implements HttpHandler {
    private SessionDao sessionDao;

    public LogoutHandler(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestURI = exchange.getRequestURI().toString();
        System.out.println(requestURI);

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
        String cookie = exchange.getRequestHeaders().getFirst("Cookie") + ";Max-age=0";
        exchange.getResponseHeaders().set("Set-Cookie", cookie);
        System.out.println("removed cookie");

        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Location", "login");
        exchange.sendResponseHeaders(302, -1);
        exchange.close();
    }
}

