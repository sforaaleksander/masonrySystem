package com.codecool.masonrysystem.controller;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.handler.*;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.helper.LoginHelper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpController {
    CookieHelper cookieHelper = new CookieHelper();
    UserDao userDao = new UserDao();
    SessionDao sessionDao = new SessionDao();
    LoginHelper loginHelper = new LoginHelper(userDao);


    public void init() throws IOException {
        int port = 8001;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/login", new LoginHandler(cookieHelper, userDao, sessionDao, loginHelper));
        server.createContext("/logout", new LogoutHandler(sessionDao, loginHelper));
        server.createContext("/console", new ConsoleHandler(cookieHelper, userDao, sessionDao));
        server.createContext("/academy", new AcademyHandler(cookieHelper, userDao, sessionDao));
        server.createContext("/sanctuary", new SanctuaryHandler(cookieHelper, userDao, sessionDao));
        server.createContext("/quest-details", new QuestViewHandler(cookieHelper, sessionDao, userDao));
        server.createContext("/artifact-details", new ArtifactViewHandler(cookieHelper, sessionDao, userDao));
        server.createContext("/static", new Static());
        server.setExecutor(null);
        server.start();
    }
}
