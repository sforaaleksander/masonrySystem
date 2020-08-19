package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.QuestDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.exception.CookieNotFoundException;
import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.helper.HandlerHelper;
import com.codecool.masonrysystem.model.Quest;
import com.codecool.masonrysystem.model.Session;
import com.codecool.masonrysystem.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Optional;

public class AcademyHandler implements HttpHandler {
    private final HandlerHelper handlerHelper;
    private final CookieHelper cookieHelper;
    private final UserDao userDao;
    private final QuestDao questDao;
    private final SessionDao sessionDao;


    public AcademyHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        this.handlerHelper = handlerHelper;
        this.cookieHelper = cookieHelper;
        this.userDao = userDao;
        this.sessionDao = sessionDao;
        this.questDao = new QuestDao();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        List<Quest> questList = null;
        User user = null;
        try {
            questList = questDao.getAll();
            Optional<HttpCookie> cookieOptional = cookieHelper.getSessionIdCookie(httpExchange, CookieHelper.getSessionCookieName());
            if (!cookieOptional.isPresent()) {
                throw new CookieNotFoundException("Expected cookie could not be found");
            }
            String sessionId = cookieHelper.getSessionIdFromCookie(cookieOptional.get());
            Session session = sessionDao.getById(sessionId);
            Long userId = session.getUserId();
            user = userDao.getById(userId);

        } catch (ElementNotFoundException | ClassNotFoundException | CookieNotFoundException e) {
            e.printStackTrace();
        }
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/academy.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("quests", questList);
        model.with("user", user);
        response = template.render(model);
        handlerHelper.send200(httpExchange, response);
    }
}
