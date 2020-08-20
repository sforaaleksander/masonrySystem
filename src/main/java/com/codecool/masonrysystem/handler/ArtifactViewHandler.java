package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.ArtifactDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.model.Artifact;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public class ArtifactViewHandler extends Handler<Artifact> implements HttpHandler {

    public ArtifactViewHandler(CookieHelper cookieHelper, SessionDao sessionDao) {
        super("element_details.twig", cookieHelper, null, sessionDao, new ArtifactDao());
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        superHandleGetSingle(httpExchange);
    }
}
