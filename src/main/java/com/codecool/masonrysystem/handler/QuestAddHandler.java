package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.QuestDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.model.Quest;
import com.codecool.masonrysystem.model.Rank;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Map;

public class QuestAddHandler extends Handler<Quest> implements HttpHandler {
    public QuestAddHandler(CookieHelper cookieHelper, SessionDao sessionDao, UserDao userDao) {
        super("add-quest.twig", cookieHelper, userDao, sessionDao, new QuestDao());
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            String response = createResponse();
            send200(httpExchange, response);
        } else {
            System.out.println("posting");
            postForm(httpExchange);
        }
    }

    private void postForm(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = getInputs(httpExchange);
        Quest quest = new Quest();
        Long id = dao.getHighestIdElement().getId() + 1;
        quest.setId(id);
        quest.setName(inputs.get("quest-name"));
        quest.setReward(Integer.valueOf(inputs.get("quest-reward")));
        quest.setRequiredRank(Rank.THEILLUMINATI); //TODO
//        quest.setRequiredRank(Rank.valueOf(inputs.get("quest-requirement")));
        quest.setDescription(inputs.get("quest-description"));
        quest.setExpirationDate(null);
        quest.setIsActive(true);
        quest.setIsCollective(false);
        dao.insert(quest);
        System.out.println("inserted quest to db");
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", "academy");
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }
}
