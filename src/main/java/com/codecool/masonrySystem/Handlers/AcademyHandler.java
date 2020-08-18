package com.codecool.masonrySystem.Handlers;

import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.codecool.masonrySystem.Helpers.HandlerHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;

public class AcademyHandler implements HttpHandler {
    private HandlerHelper handlerHelper;
    private CookieHelper cookieHelper;

    public AcademyHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper) {
        this.handlerHelper = handlerHelper;
        this.cookieHelper = cookieHelper;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/academy.twig");
        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        handlerHelper.send200(httpExchange, response);
    }
}
