package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.QuestDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.helper.HandlerHelper;
import com.codecool.masonrysystem.model.Quest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;

public class QuestHandler implements HttpHandler {
    private final HandlerHelper handlerHelper;
    private final CookieHelper cookieHelper;
    private final QuestDao questDao;
    private final SessionDao sessionDao;

    public QuestHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper, SessionDao sessionDao) {
        this.handlerHelper = handlerHelper;
        this.cookieHelper = cookieHelper;
        this.sessionDao = sessionDao;
        this.questDao = new QuestDao();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        long questId;
        Quest quest = null;
        String[] elements = httpExchange.getRequestURI().toString().split("/");
        questId = Long.parseLong(elements[elements.length - 1]);
        try {
            quest = questDao.getById(questId);
        } catch (ClassNotFoundException | ElementNotFoundException e) {
            e.printStackTrace();
        }

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/quest_details.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("quest", quest);
        response = template.render(model);
        handlerHelper.send200(httpExchange, response);
    }
}
