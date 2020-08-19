package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.ArtifactDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.model.Artifact;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class SanctuaryHandler extends Handler<Artifact> implements HttpHandler {

    public SanctuaryHandler(CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        super("sanctuary.twig", cookieHelper, userDao, sessionDao, new ArtifactDao());
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        superHandle(httpExchange);
    }
}
