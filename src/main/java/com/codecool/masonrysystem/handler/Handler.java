package com.codecool.masonrysystem.handler;

import com.codecool.masonrysystem.dao.ArtifactDao;
import com.codecool.masonrysystem.dao.SessionDao;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.exception.ElementNotFoundException;
import com.codecool.masonrysystem.helper.CookieHelper;
import com.codecool.masonrysystem.helper.HandlerHelper;
import com.codecool.masonrysystem.model.Quest;
import com.codecool.masonrysystem.model.Session;
import com.codecool.masonrysystem.model.User;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Handler<T> {
    protected final String TWIGFILENAME;
    protected Map<String, Object> modelMap;
    protected final HandlerHelper handlerHelper;
    protected final CookieHelper cookieHelper;
    protected final UserDao userDao;
    protected final SessionDao sessionDao;
    protected String response;
    protected List<T> questList = null;
    protected User user = null;


    public Handler(String twigFileName, HandlerHelper handlerHelper, CookieHelper cookieHelper, UserDao userDao, SessionDao sessionDao) {
        this.TWIGFILENAME = twigFileName;
        this.modelMap = new HashMap<>();
        this.handlerHelper = handlerHelper;
        this.cookieHelper = cookieHelper;
        this.userDao = userDao;
        this.sessionDao = sessionDao;

    }

    public void send200(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public Map<String, String> getInputs(HttpExchange httpExchange) throws IOException {
        String formData = getFormData(httpExchange);
        return parseFormData(formData);
    }

    private String getFormData(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        return br.readLine();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = URLDecoder.decode(keyValue[0], "UTF-8");
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(key, value);
        }
        return map;
    }

    public User getUserFromCookie(HttpCookie cookie) throws ElementNotFoundException, ClassNotFoundException {
        String sessionId = cookieHelper.getSessionIdFromCookie(cookie);
        Session session = sessionDao.getById(sessionId);
        Long userId = session.getUserId();
        return userDao.getById(userId);
    }

    public String createResponse() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/" + TWIGFILENAME);
        JtwigModel model = JtwigModel.newModel();
        for (Map.Entry<String, Object> entry : modelMap.entrySet()) {
            model.with(entry.getKey(), entry.getValue());
        }
        return template.render(model);
    }
}
