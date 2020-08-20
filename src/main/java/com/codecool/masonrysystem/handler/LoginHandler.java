package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.helper.LoginHelper;
import com.codecool.masonrysystem.model.Session;
import com.codecool.masonrysystem.model.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Map;
import java.util.Optional;

public class LoginHandler extends Handler<User> implements HttpHandler {
    private final LoginHelper loginHelper;


    public LoginHandler(CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        super("login.twig", cookieHelper, userDao, sessionDao, null);
        loginHelper = new LoginHelper(userDao);
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
        response = createResponse();
        send200(httpExchange, response);
    }

    private void postForm(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = getInputs(httpExchange);
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
}
