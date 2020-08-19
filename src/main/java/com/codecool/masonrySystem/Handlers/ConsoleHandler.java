package com.codecool.masonrySystem.Handlers;

import com.codecool.masonrySystem.DAO.ArtifactDao;
import com.codecool.masonrySystem.DAO.SessionDao;
import com.codecool.masonrySystem.DAO.TransactionDao;
import com.codecool.masonrySystem.DAO.UserDao;
import com.codecool.masonrySystem.Exception.CookieNotFoundException;
import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.codecool.masonrySystem.Helpers.HandlerHelper;
import com.codecool.masonrySystem.Helpers.LoginHelper;
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

public class ConsoleHandler implements HttpHandler {
    private final CookieHelper cookieHelper;
    private final HandlerHelper handlerHelper;
    private final UserDao userDao;
    private final SessionDao sessionDao;
    private final ArtifactDao artifactDao;
    private final TransactionDao transactionDao;

    public ConsoleHandler(HandlerHelper handlerHelper, CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        this.handlerHelper = handlerHelper;
        this.cookieHelper = cookieHelper;
        this.userDao = userDao;
        this.sessionDao = sessionDao;
        this.artifactDao = new ArtifactDao();
        this.transactionDao = new TransactionDao();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response;
        List<Artifact> artifactList = null;
        User user = null;
        try {
            Optional<HttpCookie> cookieOptional = cookieHelper.getSessionIdCookie(httpExchange, CookieHelper.getSessionCookieName());
            if (!cookieOptional.isPresent()) {
                throw new CookieNotFoundException("Expected cookie could not be found");
            }
            String sessionId = cookieHelper.getSessionIdFromCookie(cookieOptional.get());
            Session session = sessionDao.getById(sessionId);
            Long userId = session.getUserId();
            artifactList = artifactDao.getAllUsedByUserId(userId);
            user = userDao.getById(userId);
        } catch (ElementNotFoundException | ClassNotFoundException | CookieNotFoundException e) {
            e.printStackTrace();
        }
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/console.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("user", user);
        model.with("artifacts", artifactList);
        response = template.render(model);
        handlerHelper.send200(httpExchange, response);
    }
}
