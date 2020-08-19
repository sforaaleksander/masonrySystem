package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.ArtifactDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.exception.CookieNotFoundException;
import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.model.Artifact;
import com.codecool.masonrysystem.model.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Optional;

public class ConsoleHandler extends Handler<Artifact> implements HttpHandler {
    private final ArtifactDao artifactDao;

    public ConsoleHandler(CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        super("console.twig", cookieHelper, userDao, sessionDao, null);
        artifactDao = new ArtifactDao();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            Optional<HttpCookie> cookieOptional = cookieHelper.getSessionIdCookie(httpExchange, CookieHelper.getSessionCookieName());
            if (!cookieOptional.isPresent()) {
                throw new CookieNotFoundException("Expected cookie could not be found");
            }
            String sessionId = cookieHelper.getSessionIdFromCookie(cookieOptional.get());
            Session session = sessionDao.getById(sessionId);
            Long userId = session.getUserId();
            elementList = artifactDao.getAllUsedByUserId(userId);
            user = userDao.getById(userId);
        } catch (ElementNotFoundException | ClassNotFoundException | CookieNotFoundException e) {
            e.printStackTrace();
        }
        response = createResponse();
        send200(httpExchange, response);
    }
}
