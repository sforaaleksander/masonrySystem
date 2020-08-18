package com.codecool.masonrySystem.Handlers;

import com.codecool.masonrySystem.DAO.QuestDao;
import com.codecool.masonrySystem.DAO.UserDao;
import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.codecool.masonrySystem.Helpers.HandlerHelper;
import com.codecool.masonrySystem.Models.Quest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.util.List;

public class AcademyHandler implements HttpHandler {
    private HandlerHelper handlerHelper;
    private CookieHelper cookieHelper;
    private UserDao userDao;
    private QuestDao questDao;


    public AcademyHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper, UserDao userDao) {
        this.handlerHelper = handlerHelper;
        this.cookieHelper = cookieHelper;
        this.userDao = userDao;
        this.questDao = new QuestDao();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        List<Quest> questList = null;
        try {
            questList = questDao.getAll();
        } catch (ElementNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/academy.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("quests", questList);
        response = template.render(model);
        handlerHelper.send200(httpExchange, response);
    }
}
