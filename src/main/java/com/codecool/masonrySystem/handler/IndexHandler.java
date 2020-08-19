package com.codecool.masonrySystem.handler;

import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.codecool.masonrySystem.Helpers.HandlerHelper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;

public class IndexHandler implements HttpHandler {
    private HandlerHelper handlerHelper;
    private CookieHelper cookieHelper;

    public IndexHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper) {
        this.cookieHelper = cookieHelper;
        this.handlerHelper = handlerHelper;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/index.twig");
        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        handlerHelper.send200(httpExchange, response);
    }
}
