package com.codecool.masonrySystem.Controllers;
import com.codecool.masonrySystem.Handlers.LoginHandler;
import com.codecool.masonrySystem.Helpers.CookieHelper;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpController {
    CookieHelper cookieHelper = new CookieHelper();

    public void init() throws IOException {
        int port = 9000;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/login", new LoginHandler(cookieHelper));
    }
}
