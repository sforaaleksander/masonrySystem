package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.QuestDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.model.Quest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class QuestAddHandler extends Handler<Quest> implements HttpHandler {
    public QuestAddHandler(CookieHelper cookieHelper, SessionDao sessionDao, UserDao userDao) {
        super("add-quest.twig", cookieHelper, userDao, sessionDao, new QuestDao());
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = createResponse();
        send200(httpExchange, response);
    }
}
