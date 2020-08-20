package com.codecool.masonrysystem.controller;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.handler.*;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpController {
    CookieHelper cookieHelper = new CookieHelper();
    UserDao userDao = new UserDao();
    SessionDao sessionDao = new SessionDao();

    public void init() throws IOException {
        int port = 8001;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/login", new LoginHandler(cookieHelper, userDao, sessionDao));
        server.createContext("/logout", new LogoutHandler(sessionDao));
        server.createContext("/console", new ConsoleHandler(cookieHelper, userDao, sessionDao));
        server.createContext("/academy", new AcademyHandler(cookieHelper, userDao, sessionDao));
        server.createContext("/sanctuary", new SanctuaryHandler(cookieHelper, userDao, sessionDao));
        server.createContext("/quest", new QuestHandler(cookieHelper, sessionDao));
        server.createContext("/static", new Static());
        server.setExecutor(null);
        server.start();
    }
}
