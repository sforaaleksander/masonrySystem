package com.codecool.masonrySystem.controller;
import com.codecool.masonrySystem.dao.SessionDao;
import com.codecool.masonrySystem.dao.UserDao;
import com.codecool.masonrySystem.handler.*;
import com.codecool.masonrySystem.helper.CookieHelper;
import com.codecool.masonrySystem.helper.HandlerHelper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpController {
    HandlerHelper handlerHelper = new HandlerHelper();
    CookieHelper cookieHelper = new CookieHelper();
    UserDao userDao = new UserDao();
    SessionDao sessionDao = new SessionDao();

    public void init() throws IOException {
        int port = 8001;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/login", new LoginHandler(handlerHelper, cookieHelper, userDao, sessionDao));
        server.createContext("/logout", new LogoutHandler(sessionDao));
        server.createContext("/index", new IndexHandler(handlerHelper, cookieHelper));
        server.createContext("/console", new ConsoleHandler(handlerHelper, cookieHelper, userDao, sessionDao));
        server.createContext("/academy", new AcademyHandler(handlerHelper, cookieHelper, userDao, sessionDao));
        server.createContext("/sanctuary", new SanctuaryHandler(handlerHelper, cookieHelper, userDao, sessionDao));
        server.createContext("/static", new Static());
        server.setExecutor(null);
        server.start();
    }
}
