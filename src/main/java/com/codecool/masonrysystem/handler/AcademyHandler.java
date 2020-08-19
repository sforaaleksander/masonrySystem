package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.QuestDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.exception.CookieNotFoundException;
import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.helper.HandlerHelper;
import com.codecool.masonrysystem.model.Quest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Optional;

public class AcademyHandler extends Handler<Quest> implements HttpHandler {
    private final QuestDao questDao = new QuestDao();

    public AcademyHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        super("academy.twig", handlerHelper, cookieHelper, userDao, sessionDao);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            questList = questDao.getAll();
            Optional<HttpCookie> cookieOptional = cookieHelper.getSessionIdCookie(httpExchange, CookieHelper.getSessionCookieName());
            if (!cookieOptional.isPresent()) {
                throw new CookieNotFoundException("Expected cookie could not be found");
            }
            user = handlerHelper.getUserFromCookie(cookieOptional.get());
        } catch (ElementNotFoundException | ClassNotFoundException | CookieNotFoundException e) {
            e.printStackTrace();
        }
        modelMap.put("quests", questList);
        modelMap.put("user", user);
        response = createResponse();
        handlerHelper.send200(httpExchange, response);
    }
}
