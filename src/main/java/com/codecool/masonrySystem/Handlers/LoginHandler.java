package com.codecool.masonrySystem.Handlers;

import com.codecool.masonrySystem.DAO.SessionDao;
import com.codecool.masonrySystem.DAO.UserDao;
import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.codecool.masonrySystem.Helpers.HandlerHelper;
import com.codecool.masonrySystem.Helpers.LoginHelper;
import com.codecool.masonrySystem.Models.Session;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;
import java.util.Optional;

public class LoginHandler implements HttpHandler {
    private final CookieHelper cookieHelper;
    private final HandlerHelper handlerHelper;
    private final LoginHelper loginHelper;
    private final UserDao userDao;
    private final SessionDao sessionDao;


    public LoginHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        this.handlerHelper = handlerHelper;
        this.cookieHelper = cookieHelper;
        this.userDao = userDao;
        this.sessionDao = sessionDao;
        this.loginHelper = new LoginHelper(userDao);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("POST")) {
            postForm(httpExchange);
        } else if (method.equals("GET")) {
            handleGet(httpExchange);
        }
    }

    private void handleGet(HttpExchange httpExchange) throws IOException {
        Optional<HttpCookie> optionalCookie = cookieHelper.getSessionIdCookie(httpExchange, CookieHelper.getSessionCookieName());
        if (optionalCookie.isPresent()) {
            redirectHome(httpExchange);
        } else {
            getForm(httpExchange);
        }
    }

    private void getForm(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        handlerHelper.send200(httpExchange, response);
    }

    private void postForm(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = handlerHelper.getInputs(httpExchange);
        try {
            if (loginHelper.areCredentialsValid(inputs)) {
                login(httpExchange, inputs);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void login(HttpExchange httpExchange, Map<String, String> inputs) throws ClassNotFoundException, IOException {
        Session session;
        String sessionId = loginHelper.getIdGenerator().generateId(18);
        System.out.println(sessionId);
        Long userId = userDao.getUserByEmail(inputs.get("email")).getId();
        session = new Session();
        session.setSessionId(sessionId);
        session.setUserId(userId);
        sessionDao.insert(session);
        cookieHelper.createCookie(httpExchange, CookieHelper.getSessionCookieName(), sessionId);
        redirectHome(httpExchange);
    }

    private void redirectHome(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", "index");
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }
}
