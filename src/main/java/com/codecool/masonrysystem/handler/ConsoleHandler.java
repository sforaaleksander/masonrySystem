package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.model.Rank;
import com.codecool.masonrysystem.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class ConsoleHandler extends Handler<User> implements HttpHandler {
    private final ApprenticeConsoleHandler apprenticeConsoleHandler;
    private final UserManagerConsoleHandler userManagerConsoleHandler;

    public ConsoleHandler(CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        super("console.twig", cookieHelper, userDao, sessionDao, null);
        this.apprenticeConsoleHandler = new ApprenticeConsoleHandler(cookieHelper, userDao, sessionDao);
        this.userManagerConsoleHandler = new UserManagerConsoleHandler(cookieHelper, userDao, sessionDao);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            user = getUserFromOptionalCookie(httpExchange);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (user.getRank().equals(Rank.AINSOPHAUR) || user.getRank().equals(Rank.THEILLUMINATI)) {
            userManagerConsoleHandler.handle(httpExchange);
        } else {
            apprenticeConsoleHandler.handle(httpExchange);
        }
    }
}
