package com.codecool.masonrySystem.Controllers;
import com.codecool.masonrySystem.DAO.SessionDao;
import com.codecool.masonrySystem.DAO.UserDao;
import com.codecool.masonrySystem.Handlers.*;
import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.codecool.masonrySystem.Helpers.HandlerHelper;
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
        server.createContext("/academy", new AcademyHandler(handlerHelper, cookieHelper, userDao));
        server.createContext("/static", new Static());
        server.setExecutor(null);
        server.start();
    }
}
