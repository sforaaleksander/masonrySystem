package com.codecool.masonrySystem.Handlers;

import com.codecool.masonrySystem.DAO.UserDao;
import com.codecool.masonrySystem.Exception.InvalidLoginDataException;
import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.codecool.masonrySystem.Helpers.HandlerHelper;
import com.codecool.masonrySystem.Helpers.LoginHelper;
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
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private static int counter = 0;
    private CookieHelper cookieHelper;
    private HandlerHelper handlerHelper;
    private UserDao userDao;
    private LoginHelper loginHelper;


    public LoginHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper, UserDao userDao) {
        this.handlerHelper = handlerHelper;
        this.cookieHelper = cookieHelper;
        this.userDao = userDao;
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
        Optional<HttpCookie> optionalCookie = cookieHelper.getSessionIdCookie(httpExchange, SESSION_COOKIE_NAME);
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
                String sessionId = loginHelper.getIdGenerator().generateId(18);
                System.out.println(sessionId);
                cookieHelper.createCookie(httpExchange, SESSION_COOKIE_NAME, sessionId);
                redirectHome(httpExchange);
            }
        } catch (InvalidLoginDataException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void redirectHome(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", "index");
        httpExchange.sendResponseHeaders(302, -1);
        httpExchange.close();
    }
}
