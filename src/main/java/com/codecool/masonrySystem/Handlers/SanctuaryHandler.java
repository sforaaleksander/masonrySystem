package com.codecool.masonrySystem.Handlers;

import com.codecool.masonrySystem.DAO.ArtifactDao;
import com.codecool.masonrySystem.DAO.SessionDao;
import com.codecool.masonrySystem.DAO.UserDao;
import com.codecool.masonrySystem.Exception.CookieNotFoundException;
import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.codecool.masonrySystem.Helpers.HandlerHelper;
import com.codecool.masonrySystem.Models.Artifact;
import com.codecool.masonrySystem.Models.Session;
import com.codecool.masonrySystem.Models.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Optional;

public class SanctuaryHandler implements HttpHandler {
    private final HandlerHelper handlerHelper;
    private final CookieHelper cookieHelper;
    private final UserDao userDao;
    private final ArtifactDao artifactDao;
    private final SessionDao sessionDao;

    public SanctuaryHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        this.handlerHelper = handlerHelper;
        this.cookieHelper = cookieHelper;
        this.userDao = userDao;
        this.sessionDao = sessionDao;
        this.artifactDao = new ArtifactDao();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        List<Artifact> artifactList = null;
        User user = null;
        try {
            artifactList = artifactDao.getAll();
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
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/sanctuary.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("user", user);
        model.with("artifacts", artifactList);
        response = template.render(model);
        handlerHelper.send200(httpExchange, response);
    }
}
