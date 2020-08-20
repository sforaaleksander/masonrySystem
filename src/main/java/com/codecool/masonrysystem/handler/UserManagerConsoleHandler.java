package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.exception.CookieNotFoundException;
import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.model.Rank;
import com.codecool.masonrysystem.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Optional;

public class UserManagerConsoleHandler extends Handler<User> implements HttpHandler {
    private final int APPRENTICE_RANK_ID = 3;
    private final int JOURNEYMAN_RANK_ID = 2;

    public UserManagerConsoleHandler(CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        super("console.twig", cookieHelper, userDao, sessionDao, userDao);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            user = getUserFromOptionalCookie(httpExchange);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (user.getRank().equals(Rank.AINSOPHAUR)) {
            getConsoleViewForUserManager(httpExchange, JOURNEYMAN_RANK_ID);
        } else {
            getConsoleViewForUserManager(httpExchange, APPRENTICE_RANK_ID);
        }
    }

    private void getConsoleViewForUserManager(HttpExchange httpExchange, int rankId) throws IOException {
        try {
            Optional<HttpCookie> cookieOptional = cookieHelper.getSessionIdCookie(httpExchange, CookieHelper.getSessionCookieName());
            if (!cookieOptional.isPresent()) {
                throw new CookieNotFoundException("Expected cookie could not be found");
            }
            elementList = userDao.getUsersByRole(rankId);
        } catch (ElementNotFoundException | CookieNotFoundException e) {
            e.printStackTrace();
        }
        response = createResponse();
        send200(httpExchange, response);
    }
}
